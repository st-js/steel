package org.steel.model;

import static org.stjs.javascript.Global.console;
import static org.stjs.javascript.JSCollections.$array;
import static org.stjs.javascript.JSObjectAdapter.$js;
import static org.stjs.javascript.JSObjectAdapter.$properties;

import org.stjs.javascript.Array;
import org.stjs.javascript.JSGlobal;
import org.stjs.javascript.functions.Callback1;
import org.stjs.javascript.functions.Callback2;
import org.stjs.javascript.functions.Callback3;
import org.stjs.javascript.functions.Function0;

public class Model {
	protected static final String MODEL_PROPERTY = "$$model";
	private static int modelSequence = 0;

	private final Array<Callback3<Object, String, Object>> observers;

	private final Callback3<Object, String, Object> childrenObserver;
	private final String modelId;
	private final Object target;

	private final Callback2<Object, String> tracer;

	public Model(Object target, Callback2<Object, String> tracer) {
		this.target = target;
		this.tracer = tracer;

		modelSequence++;
		this.modelId = modelSequence + "";
		observers = $array();
		childrenObserver = this::propagateChildrenChange;

		$properties(target).$put(MODEL_PROPERTY, this);
		$properties(target).$put("set", modelSet);
		$properties(target).$put("get", modelGet);

		addModelAndObserveChildren();
	}
	private static Object modelSet = $js("function(field, value) { return this.$$model.set(field, value);}");
	private static Object modelGet = $js("function(field) { return this.$$model.get(field);}");

	public Object set(String field, Object value) {
		Object prevValue = $properties(target).$get(field);
		if (isSubModel(field, prevValue)) {
			unobserve(prevValue, childrenObserver);
		}

		$properties(target).$put(field, value);
		if (isSubModel(field, value)) {
			observe(value, childrenObserver);
		}

		//copy this array as it may be modified by one of the listeners
		Array<Callback3<Object, String, Object>> observersCopy = observers.slice(0);
		observersCopy.$forEach(obs -> obs.$invoke(target, field, value));
		return value;
	}

	public Object get(String field) {
		Object value = $properties(target).$get(field);
		if (tracer != null) {
			tracer.$invoke(target, field);
		}
		return value;
	}

	public Model doObserve(Callback3<Object, String, Object> observer) {
		observers.push(observer);
		return this;
	}

	public Model doUnobserve(Callback3<Object, String, Object> observer) {
		for (int i = 0; i < observers.$length(); i++) {
			if (observers.$get(i) == observer) {
				observers.splice(i, 1);
				return this;
			}
		}
		return this;
	}

	private void propagateChildrenChange(Object child, String field, Object value) {
		observers.$forEach(obs -> obs.$invoke(child, field, value));
	}

	public Object getTarget() {
		return target;
	}

	public String getId() {
		return modelId;
	}

	/**
	 * prepare as a model all descending children
	 * @param model
	 */
	protected void addModelAndObserveChildren() {
		forEachSubmodel(sm -> {
			model(sm);
			observe(sm, childrenObserver);
		});
	}

	protected void forEachSubmodel(Callback1<Object> exec) {
		for (String prop : $properties(target)) {
			Object propValue = $properties(target).$get(prop);
			if (isSubModel(prop, propValue)) {
				console.info("SUBMODEL:", prop);
				exec.$invoke(propValue);
			}
		}
	}

	protected boolean isSubModel(String propName, Object propValue) {
		if (propValue != null && JSGlobal.typeof(propValue) == "object" && !propName.startsWith("$$")) {
			//&& Global.stjs.getMemberAnnotation(target.getClass(), propName, "Template") != null) {
			//fix this
			return true;
		}
		return false;
	}

	private static Callback2<Object, String> currentTracer;

	private static void traceRelay(Object model, String field) {
		if (currentTracer != null) {
			currentTracer.$invoke(model, field);
		}
	}

	public static <T> T trace(Callback2<Object, String> tracer, Function0<T> expr) {
		currentTracer = tracer;
		T value = expr.$invoke();
		currentTracer = null;
		return value;
	}

	public static <T> T model(T model) {
		return (T) getModel(model).getTarget();
	}

	private static <T> Model getModel(T model) {
		Model m = (Model) $properties(model).$get(Model.MODEL_PROPERTY);
		if (m == null) {

			m = new Model(model, Model::traceRelay);
			return m;
		}
		return m;
	}

	public static void observe(Object model, Callback3<Object, String, Object> observer) {
		getModel(model).doObserve(observer);
	}

	public static void unobserve(Object model, Callback3<Object, String, Object> observer) {
		getModel(model).doUnobserve(observer);
	}

	public static String id(Object model) {
		return getModel(model).getId();
	}
}

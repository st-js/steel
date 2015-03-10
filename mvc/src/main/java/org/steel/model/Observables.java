package org.steel.model;

import static org.stjs.javascript.JSObjectAdapter.$js;
import static org.stjs.javascript.JSObjectAdapter.$properties;

import org.stjs.javascript.functions.Callback2;
import org.stjs.javascript.functions.Callback3;
import org.stjs.javascript.functions.Function0;

public class Observables {
	private static final String MODEL_PROPERTY = "$$model";
	private static Callback2<Object, String> currentTracer;
	private static int modelSequence = 0;

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
		Model m = (Model) $properties(model).$get(MODEL_PROPERTY);
		if (m == null) {
			modelSequence++;
			m = new Model(model, modelSequence + "", Observables::traceRelay);
			$properties(model).$put(MODEL_PROPERTY, m);
			$properties(model).$put("set", modelSet);
			$properties(model).$put("get", modelGet);
			return m;
		}
		return m;
	}

	private static Object modelSet = $js("function(field, value) { return this.$$model.set(field, value);}");
	private static Object modelGet = $js("function(field) { return this.$$model.get(field);}");

	public static void observe(Object model, Callback3<Object, String, Object> observer) {
		getModel(model).observe(observer);
	}

	public static void unobserve(Object model, Callback3<Object, String, Object> observer) {
		getModel(model).unobserve(observer);
	}

	public static String id(Object model) {
		return getModel(model).getId();
	}
}

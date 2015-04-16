package org.steel.model;

import static org.stjs.javascript.JSCollections.$map;

import org.stjs.javascript.Map;
import org.stjs.javascript.functions.Callback1;
import org.stjs.javascript.functions.Callback3;
import org.stjs.javascript.functions.Function0;

public class DynExpr<T> {
	private final Function0<T> expr;
	private Callback1<DynExpr<T>> observer;
	private Map<String, Object> tracedFields;
	private Map<String, Object> tracedModels;
	private Callback3<Object, String, Object> modelModifiedCallback;

	private DynExpr(Function0<T> expr) {
		this.expr = expr;
		tracedFields = $map();
		tracedModels = $map();
		modelModifiedCallback = this::onModelModified;
	}

	public static <T> DynExpr<T> of(Function0<T> expr) {
		return new DynExpr<T>(expr);
	}

	public T value() {
		for (String key : tracedModels) {
			Model.unobserve(tracedModels.$get(key), modelModifiedCallback);
		}
		tracedFields = $map();
		tracedModels = $map();
		return Model.trace(this::trace, expr);
	}

	private void trace(Object model, String field) {
		String key = Model.id(model) + ":" + field;
		if (tracedFields.$get(key) != null) {
			return;
		}

		if (tracedModels.$get(Model.id(model)) == null) {
			Model.observe(model, modelModifiedCallback);
			tracedModels.$put(Model.id(model), model);
		}
		tracedFields.$put(key, model);
	}

	private void onModelModified(Object model, String field, Object value) {
		String key = Model.id(model) + ":" + field;
		if (tracedFields.$get(key) == null) {
			return;
		}
		// TODO accumulate changes and call async
		observer.$invoke(this);
	}

	public void observe(Callback1<DynExpr<T>> callback) {
		value();
		// TODO multiple observers !?
		this.observer = callback;
	}

}

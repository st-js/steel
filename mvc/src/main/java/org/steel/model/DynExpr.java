package org.steel.model;

import static org.stjs.javascript.JSCollections.$map;

import org.stjs.javascript.Map;
import org.stjs.javascript.functions.Callback1;
import org.stjs.javascript.functions.Function0;

public class DynExpr<T> {
	private final Function0<T> expr;
	private Callback1<DynExpr<T>> observer;
	private final Map<String, Object> tracedFields;
	private final Map<String, Object> tracedModels;

	private DynExpr(Function0<T> expr) {
		this.expr = expr;
		tracedFields = $map();
		tracedModels = $map();
	}

	public static <T> DynExpr<T> of(Function0<T> expr) {
		return new DynExpr<T>(expr);
	}

	public T value() {
		return Observables.trace(this::trace, expr);
	}

	private void trace(Object model, String field) {
		String key = Observables.id(model) + ":" + field;
		if (tracedFields.$get(key) != null) {
			return;
		}
		if (tracedModels.$get(Observables.id(model)) == null) {
			Observables.observe(model, this::onModelModified);
			tracedModels.$put(Observables.id(model), model);
		}
		tracedFields.$put(key, model);
	}

	private void onModelModified(Object model, String field, Object value) {
		String key = Observables.id(model) + ":" + field;
		if (tracedFields.$get(key) == null) {
			return;
		}
		//TODO accumulate changes and call async
		observer.$invoke(this);
	}

	public void observe(Callback1<DynExpr<T>> callback) {
		value();
		//TODO multiple observers !?
		this.observer = callback;
	}

}

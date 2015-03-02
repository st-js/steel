package org.steel.model;

import static org.stjs.javascript.JSCollections.$array;
import static org.stjs.javascript.JSObjectAdapter.$properties;

import org.stjs.javascript.Array;
import org.stjs.javascript.functions.Callback2;
import org.stjs.javascript.functions.Callback3;

public class Model {
	private final Array<Callback3<Object, String, Object>> observers;

	private final String id;
	private final Object target;

	private final Callback2<Object, String> tracer;

	public Model(Object target, String id, Callback2<Object, String> tracer) {
		this.target = target;
		this.tracer = tracer;
		this.id = id;
		observers = $array();
	}

	public Object set(String field, Object value) {
		$properties(target).$put(field, value);
		observers.$forEach(obs -> obs.$invoke(target, field, value));
		return value;
	}

	public Object get(String field) {
		Object value = $properties(target).$get(field);
		if (tracer != null) {
			tracer.$invoke(value, field);
		}
		return value;
	}

	public Model observe(Callback3<Object, String, Object> observer) {
		observers.push(observer);
		return this;
	}

	public Model unobserve(Callback3<Object, String, Object> observer) {
		for (int i = 0; i < observers.$length(); i++) {
			if (observers.$get(i) == observer) {
				observers.splice(i, 1);
				return this;
			}
		}
		return this;
	}

	public Object getTarget() {
		return target;
	}

	public String getId() {
		return id;
	}

}

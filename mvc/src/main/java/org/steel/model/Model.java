package org.steel.model;

import static org.stjs.javascript.JSCollections.$array;
import static org.stjs.javascript.JSObjectAdapter.$properties;

import org.stjs.javascript.Array;

public class Model {
	private final Array<ModelObserver> observers;

	public Model() {
		observers = $array();
	}

	public Object set(String field, Object value) {
		$properties(this).$put(field, value);
		observers.$forEach(obs -> obs.update(this, field, value));
		return value;
	}

	public Model observe(ModelObserver observer) {
		observers.push(observer);
		return this;
	}

	public Model unobserve(ModelObserver observer) {
		for (int i = 0; i < observers.$length(); i++) {
			if (observers.$get(i) == observer) {
				observers.splice(i, 1);
				return this;
			}
		}
		return this;
	}
}

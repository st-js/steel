package org.steel.model;

import org.stjs.javascript.annotation.JavascriptFunction;

@JavascriptFunction
public interface ModelObserver {
	void update(Model model, String field, Object value);
}

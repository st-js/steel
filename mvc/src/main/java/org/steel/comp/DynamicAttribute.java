package org.steel.comp;

import static org.stjs.javascript.JSObjectAdapter.$properties;

import org.steel.model.DynExpr;
import org.stjs.javascript.dom.Element;

public class DynamicAttribute {
	private Element element;
	private final DynExpr<?> expr;
	private final String name;

	public DynamicAttribute(String name, DynExpr<?> expr) {
		this.name = name;
		this.expr = expr;
		expr.observe(e -> update());
	}

	public DynExpr<?> getExpr() {
		return expr;
	}

	protected String getTextValue() {
		Object value = expr.value();
		//TODO here you may need formatting
		return value != null ? value.toString() : "";
	}

	public void setTo(Element anElement) {
		this.element = anElement;
		update();
	}

	public void update() {
		$properties(element).$put(name, getTextValue());
	}
}

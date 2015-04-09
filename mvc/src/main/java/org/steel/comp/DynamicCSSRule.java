package org.steel.comp;

import org.steel.css.CSSStatus;
import org.steel.model.DynExpr;
import org.stjs.javascript.JSStringAdapter;
import org.stjs.javascript.RegExp;
import org.stjs.javascript.dom.Element;

public class DynamicCSSRule {
	private final CSSStatus rule;
	private final DynExpr<Boolean> expr;
	private Element element;

	public DynamicCSSRule(CSSStatus rule, DynExpr<Boolean> expr) {
		this.rule = rule;
		this.expr = expr;
		expr.observe(e -> update());
	}

	public void update() {
		String cls = element.className;
		if (cls != null && cls.length() > 0) {
			//clear first the rule - here can clean all the status of the same group
			cls = JSStringAdapter.replace(cls, new RegExp("\\b\\s*" + rule.name + "\\b", "gi"), "");
		}
		if (expr.value()) {
			cls += " " + rule.name;
		}
		element.className = cls;
	}

	public void setTo(Element domElement) {
		element = domElement;
		update();
	}

}

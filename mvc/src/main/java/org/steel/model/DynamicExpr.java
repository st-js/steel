package org.steel.model;

import static org.stjs.javascript.Global.window;

import org.stjs.javascript.Global;
import org.stjs.javascript.JSFunctionAdapter;
import org.stjs.javascript.RegExp;
import org.stjs.javascript.RegExpMatch;
import org.stjs.javascript.dom.Text;
import org.stjs.javascript.functions.Function0;
import org.stjs.javascript.jquery.JQueryCore;

public class DynamicExpr {
	public static final RegExp CLEAN_EXPR = new RegExp("(?:\\.*expr\\()(.+)\\)", "");
	private Object value;
	private final String expr;
	private Text textNode;
	private Function0<Object> exprFunc;

	public DynamicExpr(Object value, String expr) {
		this.value = value;
		RegExpMatch match = CLEAN_EXPR.exec(expr);
		if (match == null) {
			throw new RuntimeException("Cannot parse expression:" + expr);
		}
		String clean = match.$get(1);
		this.expr = clean;
		this.exprFunc = () -> Global.eval(clean);

	}

	public String getExpr() {
		return expr;
	}

	public Object getValue() {
		return value;
	}

	public void appendTo(JQueryCore<?> container) {
		textNode = window.document.createTextNode(value != null ? value.toString() : "");
		container.append(textNode);
	}

	public void update(Object context) {
		this.value = JSFunctionAdapter.call(exprFunc, context);
		textNode.textContent = value != null ? value.toString() : "";
	}

}

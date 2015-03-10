package org.steel.html;

import static org.stjs.javascript.Global.window;

import org.steel.model.DynExpr;
import org.stjs.javascript.dom.Text;
import org.stjs.javascript.jquery.JQueryCore;

public class DynamicTextNode {
	private Text textNode;
	private final DynExpr<?> expr;

	public DynamicTextNode(DynExpr<?> expr) {
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

	public void appendTo(JQueryCore<?> container) {
		textNode = window.document.createTextNode(getTextValue());
		container.append(textNode);
	}

	public void update() {
		textNode.textContent = getTextValue();
	}
}

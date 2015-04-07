package org.steel.example.todo.item;

import org.steel.css.CSSRule;
import org.steel.css.CSSStatus;

public class TodoItemCSS extends CSSRule {

	public CSSRule view;
	public CSSRule toggle;
	public CSSRule destroy;
	public CSSRule edit;
	public CSSRule label;

	public CSSStatus editing;
	public CSSStatus done;

	public TodoItemCSS() {
		super(".todo");
		editing = status("editing");
		done = status("done");

		padding = "18px 20px 18px 0";
		position = "relative";
		fontSize = "24px";
		borderBottom = "1px solid #cccccc";

		label = new CSSRule(".label");

		label.when(editing).display = "none";

		label.when(done).color = "#777777";
		label.when(done).textDecoration = "line-through";

		edit = new CSSRule(".edit");
		edit.display = "none";
		edit.width = "466px";
		edit.fontSize = "24px";
		edit.fontFamily = "inherit";
		edit.lineHeight = "1.4em";
		edit.border = "0";
		edit.outline = "none";
		edit.padding = "6px";
		edit.border = "1px solid #999999";
		edit.boxShadow = "rgba(0, 0, 0, 0.2) 0 1px 2px 0 inset";

		edit.when(editing).display = "block";
		edit.when(editing).width = "444px";
		edit.when(editing).padding = "13px 15px 14px 20px";
		edit.when(editing).margin = "0";

		destroy = new CSSRule(".destroy");
		destroy.position = "absolute";
		destroy.right = "5px";
		destroy.top = "20px";
		destroy.display = "none";
		destroy.cursor = "pointer";
		destroy.width = "20px";
		destroy.height = " 20px";
		destroy.background = "url(http://backbonejs.org/examples/todos/destroy.png) no-repeat";

		destroy.when(hover()).display = "block";
		destroy.when(destroy.hover()).backgroundPosition = "0 -20px";
	}
}

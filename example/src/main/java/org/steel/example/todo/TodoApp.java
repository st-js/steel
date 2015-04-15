package org.steel.example.todo;

import org.steel.css.CSSStyleSheet;
import org.steel.example.todo.list.TodoListView;
import org.stjs.javascript.dom.Element;

public class TodoApp {

	public void appendTo(Element container) {
		CSSStyleSheet stylesheet = new CSSStyleSheet();
		TodoAppCSS css = new TodoAppCSS();
		stylesheet.addRule(css);
		new TodoListView().css(css.list).appendTo(container);
	}

}

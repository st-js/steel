package org.steel.example.todo.item;

import org.steel.css.CSSRule;

public class TodoItemCSS extends CSSRule {

	public CSSRule view;
	public CSSRule toggle;
	public CSSRule destroy;
	public CSSRule edit;

	public TodoItemCSS(String selector) {
		super(selector);
	}

}

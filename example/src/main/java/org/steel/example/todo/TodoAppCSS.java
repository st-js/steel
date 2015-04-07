package org.steel.example.todo;

import org.steel.css.CSSRule;
import org.steel.example.todo.list.TodoListCSS;

public class TodoAppCSS extends CSSRule {
	public TodoListCSS list;

	public TodoAppCSS() {
		super("");
		list = new TodoListCSS();
	}

}

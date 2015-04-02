package org.steel.example.todo;

import org.steel.example.todo.list.TodoListView;
import org.stjs.javascript.dom.Element;

public class TodoApp {

	public void appendTo(Element container) {
		new TodoListView().appendTo(container);
	}

}

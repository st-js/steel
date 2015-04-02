package org.steel.example.todo.list;

import static org.steel.html.HTML.a;
import static org.steel.html.HTML.div;
import static org.steel.html.HTML.footer;
import static org.steel.html.HTML.h1;
import static org.steel.html.HTML.header;
import static org.steel.html.HTML.input;
import static org.steel.html.HTML.label;
import static org.steel.html.HTML.li;
import static org.steel.html.HTML.section;
import static org.steel.html.HTML.ul;
import static org.stjs.javascript.JSCollections.$array;

import org.steel.example.todo.TodoAppCSS;
import org.steel.example.todo.item.TodoItem;
import org.steel.example.todo.item.TodoItemView;
import org.steel.html.Tag;
import org.stjs.javascript.Array;
import org.stjs.javascript.dom.DOMEvent;
import org.stjs.javascript.dom.Element;
import org.stjs.javascript.dom.Input;

public class TodoListView extends Tag<TodoListView> {
	private final Array<TodoItem> todos;

	public TodoListView() {
		super("div", new TodoAppCSS(".todo"));
		todos = $array();
	}

	public TodoListView addItem(TodoItem item) {
		todos.push(item);
		return this;
	}

	@Override
	public TodoListView appendTo(Element container) {
		// @formatter:off
		id("todoapp");
		html(
			header(null).html(
				h1(null).text("todo"), //
				input(null).type("text").id("new-todo").placeholder("What needs to be done?").onchange(this::onNewTodo)),//
			section(null).id("main").html(
				input(null).id("toggle-all").type("checkbox"),
				label(null).$for("toggle-all").text("Mark all as complete"),
				ul(null).id("todo-list").html(todos, (todo, index, array) -> new TodoItemView().todo(todo), () -> li(null).text("No todo"))),//
			footer(null).html(//
				a(null).id("clear-completed").text("Clear completed"), //
				div(null).id("todo-count").text(() -> "" + completed())));
		return super.appendTo(container);
		// @formatter:on
	}

	protected int completed() {
		return todos.$length();
	}

	private boolean onNewTodo(DOMEvent ev) {
		addItem(new TodoItem(((Input) ev.target).value, false));
		return false;
	}
}

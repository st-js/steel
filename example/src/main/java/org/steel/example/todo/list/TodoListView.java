package org.steel.example.todo.list;

import static org.steel.html.HTML.a;
import static org.steel.html.HTML.div;
import static org.steel.html.HTML.footer;
import static org.steel.html.HTML.h1;
import static org.steel.html.HTML.header;
import static org.steel.html.HTML.inputCheckbox;
import static org.steel.html.HTML.inputText;
import static org.steel.html.HTML.label;
import static org.steel.html.HTML.li;
import static org.steel.html.HTML.section;
import static org.steel.html.HTML.ul;
import static org.stjs.javascript.JSCollections.$array;

import org.steel.example.todo.item.TodoItem;
import org.steel.example.todo.item.TodoItemView;
import org.steel.html.Tag;
import org.stjs.javascript.Array;
import org.stjs.javascript.dom.DOMEvent;
import org.stjs.javascript.dom.Element;
import org.stjs.javascript.dom.Input;

public class TodoListView extends Tag<TodoListView> {
	private final Array<TodoItem> todos;

	private TodoListCSS css;

	public TodoListView(TodoListCSS myCss) {
		super("div", myCss);
		todos = $array();
		css = myCss;
	}

	public TodoListView addItem(TodoItem item) {
		todos.push(item);
		return this;
	}

	@Override
	public TodoListView appendTo(Element container) {
		// @formatter:off
		html(
			header(css.header).html(
				h1(css.title).text("todo"), //
				inputText(css.newTodo).placeholder("What needs to be done?").onchange(this::onNewTodo)),//
			section(null).html(
				inputCheckbox(null),
				label(null).$for("toggle-all").text("Mark all as complete"),
				ul(css.itemList).html(todos, (todo, index, array) -> new TodoItemView(css.item).todo(todo).ondelete(this::onDeleteItem), () -> li(null).text("No todo"))),//
			footer(null).html(//
				a(null).text("Clear completed"), //
				div(null).text(() -> "" + completed())));
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

	private void onDeleteItem(TodoItem item) {
		todos.splice(todos.indexOf(item));
	}
}

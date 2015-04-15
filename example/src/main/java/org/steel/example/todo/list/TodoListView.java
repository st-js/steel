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
import org.steel.html.HTMLTag;
import org.stjs.javascript.Array;
import org.stjs.javascript.dom.DOMEvent;
import org.stjs.javascript.dom.Element;
import org.stjs.javascript.dom.Input;

public class TodoListView extends HTMLTag<TodoListView> {
	private final Array<TodoItem> todos;

	public TodoListView() {
		super("div");
		todos = $array();
	}

	public TodoListView addItem(TodoItem item) {
		todos.push(item);
		return this;
	}

	@Override
	public TodoListView appendTo(Element container) {
		TodoListCSS css = (TodoListCSS) getCssRule();
		// @formatter:off
		html(
			header().css(css.header).html(
				h1().css(css.title).text("todo"), //
				inputText().css(css.newTodo).placeholder("What needs to be done?").onchange(this::onNewTodo)),//
			section().html(
				inputCheckbox().id("toggle-all").onclick(this::onToggleAll),
				label().$for("toggle-all").text("Mark all as complete"),
				ul().css(css.itemList).html(todos, (todo, index, array) -> new TodoItemView().css(css.item).todo(todo).ondelete(this::onDeleteItem), () -> li().text("No todo"))),//
			footer().html(//
				a().text("Clear completed"), //
				div().text(() -> "" + completed())));
		return super.appendTo(container);
		// @formatter:on
	}

	protected int completed() {
		int completed = 0;
		for (int i = 0; i < todos.$length(); ++i) {
			if (todos.$get(i).done) {
				completed++;
			}
		}
		return completed;
	}

	private boolean onNewTodo(DOMEvent ev) {
		addItem(new TodoItem(((Input) ev.target).value, false));
		return false;
	}

	private boolean onToggleAll(DOMEvent ev) {
		boolean checked = ((Input) ev.target).checked;
		todos.$forEach(item -> item.done = checked);
		return true;
	}

	private void onDeleteItem(TodoItem item) {
		todos.splice(todos.indexOf(item));
	}
}

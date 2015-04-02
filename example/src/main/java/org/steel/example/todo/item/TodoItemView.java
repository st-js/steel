package org.steel.example.todo.item;

import static org.steel.html.HTML.a;
import static org.steel.html.HTML.div;
import static org.steel.html.HTML.input;
import static org.steel.html.HTML.label;

import org.steel.html.Tag;
import org.steel.model.Observables;
import org.stjs.javascript.annotation.Template;
import org.stjs.javascript.dom.Element;

public class TodoItemView extends Tag<TodoItemView> {
	@Template("property")
	private TodoItem item;
	private TodoItemCSS css;

	public TodoItemView() {
		super("li", new TodoItemCSS(".todo"));
		item = new TodoItem("", false);
		css = (TodoItemCSS) getCssRule();
		Observables.model(this);
	}

	public TodoItemView todo(TodoItem anItem) {
		item = anItem;
		return this;
	}

	@Override
	public TodoItemView appendTo(Element container) {
		// @formatter:off
		html(
			div(css.view).html(
				input(css.toggle).type("checkbox").checked(()->item.done), //
				label(null).text(() -> item.task),//
				a(css.destroy)),
			input(css.edit).type("text").value(() -> item.task));
		return super.appendTo(container);
		// @formatter:on
	}
}

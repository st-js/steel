package org.steel.example;

import static org.steel.html.HTML.div;
import static org.steel.html.HTML.expr;
import static org.steel.html.HTML.span;
import static org.stjs.javascript.JSCollections.$map;

import org.stjs.javascript.jquery.JQueryCore;

public class MyTestComponent {
	private MyModel model;

	public MyTestComponent(MyModel model) {
		this.model = model;
	}

	public void render(JQueryCore<?> container) {
		div(//
			div("a div"), //
			span("Time:", expr(() -> this.model.name() + "=" + this.model.counter))//
		).appendTo(container).$.css($map("position", "absolute", "top", "0", "left", "0"));
	}

	/*
	private public void todo() {
		TodoCSS css;
		section(
			css,//
			header(css.header,//
				h1("todos"),//
				inputText().placeholder("text")), //
			section(css.main,//
				checkbox(css.main.markAll).value(model.allChecked).change(val -> this::markAll),//
				label().$for(), //
				ul(css.main.list, () -> {
				}, //
					inputText(css.main.edit).escape(this::revertEditing).blur(this::doneEditing).focus(this::edit))).show(
				() -> model.todos.$length > 0));
	}
	*/
	//	public void test() {
	//		model.observe((obj, field, value) -> {//
	//		});
	//	}

}

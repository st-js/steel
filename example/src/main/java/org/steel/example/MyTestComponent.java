package org.steel.example;

import static org.steel.html.HTML.div;
import static org.steel.html.HTML.span;
import static org.stjs.javascript.Global.setInterval;
import static org.stjs.javascript.JSCollections.$map;
import static org.stjs.javascript.jquery.GlobalJQuery.$;

import org.steel.html.HTML;
import org.stjs.javascript.jquery.JQueryCore;

public class MyTestComponent {
	private MyTestModel model;

	public MyTestComponent(MyTestModel model) {
		this.model = model;
	}

	public void render(JQueryCore<?> container) {
		div(//
			div("a div"), //
			span("Time:", HTML.expr(this.model.value + "=" + this.model.counter)),//
			span(" Anoter Time:", HTML.expr(this.model.value + " XYZ"))//
		).appendTo(container).context(this).$.css($map("position", "absolute", "top", "0", "left", "0"));

	}

	//	public void test() {
	//		model.observe((obj, field, value) -> {//
	//		});
	//	}

	public static void dotest() {
		MyTestModel model = new MyTestModel();
		model.value = "first";

		MyTestComponent comp = new MyTestComponent(model);
		comp.render($("body"));

		setInterval(() -> {
			//model.value = "seconds:" + new Date();
			model.counter++;
		}, 1000);
	}
}

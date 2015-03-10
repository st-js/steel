package org.steel.example;

import static org.stjs.javascript.Global.console;
import static org.stjs.javascript.Global.setInterval;
import static org.stjs.javascript.Global.window;
import static org.stjs.javascript.jquery.GlobalJQuery.$;

public class TodoApp {
	public static void main(String[] args) {
		console.info("STARTED");

		$(window.document).ready((ev, THIS) -> {
			MyModel model = new MyModel();
			MyTestComponent comp = new MyTestComponent(model);
			comp.render($("body"));

			setInterval(() -> {
				//model.value = "seconds:" + new Date();
				model.counter++;
			}, 1000);

			return false;
		});
	}
}

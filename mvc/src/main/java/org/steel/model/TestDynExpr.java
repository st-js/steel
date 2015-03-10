package org.steel.model;

import static org.stjs.javascript.Global.console;

import org.stjs.javascript.annotation.Template;

public class TestDynExpr {
	public static class MyModel {
		@Template("property")
		public String firstName;

		@Template("property")
		public String lastName;

		@Template("property")
		public String title;

		public String name() {
			if (title == "mr") {
				return "Mister " + firstName + " " + lastName;
			}
			return "Madame " + lastName;
		}
	}

	public static void main(String[] args) {
		MyModel model = Observables.model(new MyModel());

		model.title = "mr";
		DynExpr.of(() -> "Check this:" + model.name()).observe((expr) -> console.info("NEW value:" + expr.value()));

		model.firstName = "a";
		model.lastName = "b";

		model.firstName = "ABC";

		console.info("change title");
		model.title = "mrs";

		console.info("change first name: should not change");
		model.firstName = "nothing";

		console.info("change last name: should change");
		model.lastName = "xxx";
	}
}

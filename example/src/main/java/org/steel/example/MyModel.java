package org.steel.example;

import org.steel.model.Observables;
import org.stjs.javascript.annotation.Template;

public class MyModel {
	@Template("property")
	public String firstName;

	@Template("property")
	public String lastName;

	@Template("property")
	public String title;

	@Template("property")
	public int counter = 0;

	public MyModel() {
		Observables.model(this);
	}

	public String name() {
		return firstName + " " + lastName;
	}

	//	public static void main(String[] args) {
	//		MyModel model = Observables.model(new MyModel());
	//
	//		DynExpr.of(() -> model.name()).observe((expr) -> console.info("NEW value:" + expr.value()));
	//
	//		model.firstName = "a";
	//	}
}
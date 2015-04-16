package org.steel.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.stjs.javascript.Reference;
import org.stjs.javascript.annotation.Template;
import org.stjs.testing.driver.STJSTestDriverRunner;

@RunWith(STJSTestDriverRunner.class)
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

	@Test
	public void testExpressionChanges() {
		//given
		MyModel model = Model.model(new MyModel());
		model.title = "mr";
		model.firstName = "a";
		model.lastName = "x";

		Reference<String> currentValue = new Reference<>();
		Reference<Boolean> executed = new Reference<>();
		DynExpr.of(() -> model.name()).observe((expr) -> {
			currentValue.value = expr.value();
			executed.value = true;
		});

		//when / then -> change value in active branch
		model.firstName = "b";
		assertEquals("Mister b x", currentValue.value);

		//when / then -> change the branching condition
		model.title = "mrs";
		assertEquals("Madame x", currentValue.value);

		//when / then -> change value in inactive branch
		executed.value = false;
		model.firstName = "b";
		assertEquals("Madame x", currentValue.value);
		assertEquals(false, executed.value);

		//when / then  -> change value in active branch
		model.lastName = "y";
		assertEquals("Madame y", currentValue.value);
	}
}

package org.steel.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.stjs.javascript.Reference;
import org.stjs.javascript.annotation.Template;
import org.stjs.javascript.functions.Callback3;
import org.stjs.testing.driver.STJSTestDriverRunner;

@RunWith(STJSTestDriverRunner.class)
public class TestObservables {
	public static class MyModel1 {
		@Template("property")
		public String field;

	}

	public static class MyModel2 {
		@Template("property")
		public MyModel1 innerModel;

	}

	@Test
	public void testObserveOneField() {
		//given
		MyModel1 model = Model.model(new MyModel1());
		Reference<Object> refModel = new Reference<>();
		Reference<String> refField = new Reference<>();
		Reference<Object> refValue = new Reference<>();

		Model.observe(model, (m, field, value) -> {
			refModel.value = m;
			refField.value = field;
			refValue.value = value;
		});

		//when
		model.field = "a value";

		//then
		assertEquals(model, refModel.value);
		assertEquals("field", refField.value);
		assertEquals("a value", refValue.value);
	}

	@Test
	public void testUnobserveOneField() {
		//given
		MyModel1 model = Model.model(new MyModel1());

		Reference<Object> executed = new Reference<>();
		Callback3<Object, String, Object> callback = (m, field, value) -> executed.value = true;
		Model.observe(model, callback);

		//when
		Model.unobserve(model, callback);
		executed.value = false;
		model.field = "a value";

		//then
		assertEquals(false, executed.value);

	}

	@Test
	public void testTraceOneField() {
		//given
		MyModel1 model = Model.model(new MyModel1());

		Reference<Object> refModel = new Reference<>();
		Reference<String> refField = new Reference<>();

		//when
		Model.trace((m, field) -> {
			refModel.value = m;
			refField.value = field;
		}, () -> model.field);

		//then
		assertEquals(model, refModel.value);
		assertEquals("field", refField.value);
	}

	@Test
	public void testObserveInnerField() {
		//given
		MyModel2 model = Model.model(new MyModel2());
		model.innerModel = Model.model(new MyModel1());

		Reference<Object> refModel = new Reference<>();
		Reference<String> refField = new Reference<>();
		Reference<Object> refValue = new Reference<>();

		Model.observe(model, (m, field, value) -> {
			refModel.value = m;
			refField.value = field;
			refValue.value = value;
		});

		//when
		model.innerModel.field = "a value";

		//then
		assertEquals(model.innerModel, refModel.value);
		assertEquals("field", refField.value);
		assertEquals("a value", refValue.value);
	}

	@Test
	public void testObserveUnregisterInnerField() {
		//given
		MyModel2 model = Model.model(new MyModel2());
		MyModel1 oldModel = Model.model(new MyModel1());
		MyModel1 newModel = Model.model(new MyModel1());
		model.innerModel = oldModel;

		Reference<Boolean> executed = new Reference<>();

		Model.observe(model, (m, field, value) -> {
			executed.value = true;
		});

		//when
		model.innerModel = newModel;
		executed.value = false;
		oldModel.field = "a value";

		//then (old model is no longer observed)
		assertEquals(false, executed.value);
	}
}

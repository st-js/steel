package org.steel.example;

import org.steel.model.Model;
import org.stjs.javascript.annotation.Template;

public class MyTestModel extends Model {
	@Template("setter")
	public String value;

	@Template("setter")
	public int counter = 0;

}

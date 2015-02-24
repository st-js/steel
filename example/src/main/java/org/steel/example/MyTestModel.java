package org.steel.example;

import org.steel.model.Model;
import org.stjs.javascript.annotation.Template;

public class MyTestModel extends Model {
	@Template("property")
	public String value;

	@Template("property")
	public int counter = 0;

	public Object allChecked;

}

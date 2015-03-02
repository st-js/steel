package org.steel.example;

import org.stjs.javascript.annotation.Template;

public class MyTestModel {
	@Template("property")
	public String value;

	@Template("property")
	public int counter = 0;

	public Object allChecked;

}

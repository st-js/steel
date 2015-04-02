package org.steel.example.todo.item;

import org.steel.model.Observables;
import org.stjs.javascript.annotation.Template;

public class TodoItem {
	@Template("property")
	public String task;

	@Template("property")
	public boolean done;

	public TodoItem(String task, boolean done) {
		Observables.model(this);
		this.task = task;
		this.done = done;
	}

}

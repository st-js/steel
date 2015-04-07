package org.steel.example.todo.list;

import org.steel.css.CSSRule;
import org.steel.example.todo.item.TodoItemCSS;

public class TodoListCSS extends CSSRule {

	public CSSRule header;
	public CSSRule main;
	public TodoItemCSS item;
	public CSSRule newTodo;
	public CSSRule itemList;
	public CSSRule title;

	public TodoListCSS() {
		super(".list");

		background = "#fff";
		padding = "20px";
		marginBottom = "40px";
		boxShadow = "rgba(0, 0, 0, 0.2) 0 2px 6px 0";
		borderRadius = "0 0 5px 5px";

		header = new CSSRule(".header");
		itemList = new CSSRule(".item-list");
		newTodo = new CSSRule(".new-todo");

		item = new TodoItemCSS();
		itemList.margin = "10px 0";
		itemList.padding = "0";
		itemList.listStyle = "none";

		title = new CSSRule(".title");
		title.fontSize = "36px";
		title.fontWeight = "bold";
		title.textAlign = "center";
		title.padding = " 0 0 10px 0";
	}
}

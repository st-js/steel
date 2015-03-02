package org.steel.html;

import static org.stjs.javascript.JSCollections.$array;
import static org.stjs.javascript.JSObjectAdapter.$properties;

import org.steel.model.DynamicExpr;
import org.steel.model.Model;
import org.stjs.javascript.Array;
import org.stjs.javascript.functions.Callback1;
import org.stjs.javascript.jquery.GlobalJQuery;
import org.stjs.javascript.jquery.JQueryCore;

public class Tag {
	public JQueryCore<?> $;
	public final String tagName;
	public Array<Model> models;
	private Array<Object> children;
	private Array<DynamicExpr> expressions;
	private Object rootContext;

	public Tag(String tagName) {
		this.tagName = tagName;
		models = $array();
		children = $array();
		expressions = $array();
	}

	public Tag model(Model model) {
		models.push(model);
		return this;
	}

	public Tag context(Object aContext) {
		this.rootContext = aContext;
		for (String property : $properties(rootContext)) {
			Object field = $properties(rootContext).$get(property);
			if (field instanceof Model) {
				((Model) field).observe(this::modelUpdate);
			}
		}
		forTags(tag -> tag.context(aContext));
		return this;
	}

	private void forTags(Callback1<Tag> callback) {
		children.$forEach(child -> {
			if (child instanceof Tag) {
				callback.$invoke((Tag) child);
			}
		});
	}

	public Tag tag(Tag tag) {
		children.push(tag);
		return this;
	}

	public Tag text(String text) {
		children.push(text);
		return this;
	}

	public Tag expr(DynamicExpr expr) {
		children.push(expr);
		expressions.push(expr);
		return this;
	}

	private void modelUpdate(Object model, String field, Object value) {
		//TODO here filter the expressions depending on the field
		expressions.$forEach(expr -> expr.update(rootContext));
	}

	public Tag appendTo(JQueryCore<?> container) {
		this.$ = GlobalJQuery.$("<" + tagName + "></" + tagName + ">").appendTo(container);
		children.$forEach(child -> {
			if (child instanceof Tag) {
				((Tag) child).appendTo($);
			} else if (child instanceof DynamicExpr) {
				((DynamicExpr) child).appendTo($);
			} else {
				$.append(child.toString());
			}
		});
		return this;
	}
}

package org.steel.html;

import static org.stjs.javascript.JSCollections.$array;

import org.steel.model.DynExpr;
import org.stjs.javascript.Array;
import org.stjs.javascript.functions.Callback1;
import org.stjs.javascript.jquery.GlobalJQuery;
import org.stjs.javascript.jquery.JQueryCore;

public class Tag {
	public JQueryCore<?> $;
	public final String tagName;
	private Array<Object> children;
	private Array<DynExpr<?>> expressions;
	private Object rootContext;

	public Tag(String tagName) {
		this.tagName = tagName;
		children = $array();
		expressions = $array();
	}

	//	public Tag context(Object aContext) {
	//		this.rootContext = aContext;
	//		for (String property : $properties(rootContext)) {
	//			Object field = $properties(rootContext).$get(property);
	//			if (field instanceof Model) {
	//				((Model) field).observe(this::modelUpdate);
	//			}
	//		}
	//		forTags(tag -> tag.context(aContext));
	//		return this;
	//	}

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

	public Tag expr(DynExpr<?> expr) {
		children.push(new DynamicTextNode(expr));
		expressions.push(expr);
		return this;
	}

	public Tag appendTo(JQueryCore<?> container) {
		this.$ = GlobalJQuery.$("<" + tagName + "></" + tagName + ">").appendTo(container);
		children.$forEach(child -> {
			if (child instanceof Tag) {
				((Tag) child).appendTo($);
			} else if (child instanceof DynamicTextNode) {
				((DynamicTextNode) child).appendTo($);
			} else {
				$.append(child.toString());
			}
		});
		return this;
	}
}

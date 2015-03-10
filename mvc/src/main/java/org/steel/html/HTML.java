package org.steel.html;

import static org.stjs.javascript.JSCollections.$array;
import static org.stjs.javascript.JSCollections.$castArray;

import org.steel.model.DynExpr;
import org.stjs.javascript.Array;
import org.stjs.javascript.functions.Function0;

public class HTML {
	/**
	 * usage: [cssRule], [tags, text, expr]*
	 * @param arguments
	 * @return
	 */
	public static Tag div(Object... arguments) {
		return tag("div", $castArray(arguments));
	}

	public static Tag p(Object... arguments) {
		return tag("p", $castArray(arguments));
	}

	public static Tag span(Object... arguments) {
		return tag("span", $castArray(arguments));
	}

	private static Array<Object> add(String param, Array<Object> args) {
		Array<Object> ret = $array(param);

		return ret;
	}

	public static <T> DynExpr<T> expr(Function0<T> e) {
		return DynExpr.of(e);
	}

	public static Tag tag(String tagName, Array<Object> args) {
		//console.info("TAG", args);
		Tag tag = new Tag(tagName);
		for (int i = 0; i < args.$length(); ++i) {
			Object arg = args.$get(i);
			if (arg instanceof Tag) {
				tag.tag((Tag) arg);
			} else if (arg instanceof DynExpr) {
				tag.expr((DynExpr<?>) arg);
			} else {
				tag.text((String) arg);
			}
		}
		return tag;
	}

}

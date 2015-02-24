package org.steel.html;

import static org.stjs.javascript.JSCollections.$array;
import static org.stjs.javascript.JSCollections.$castArray;
import static org.stjs.javascript.JSObjectAdapter.$properties;

import org.steel.model.DynamicExpr;
import org.stjs.javascript.Array;

public class HTML extends HTMLBridge {
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

	public static Tag tag(String tagName, Array<Object> args) {
		//console.info("TAG", args);
		Tag tag = new Tag(tagName);
		for (int i = 0; i < args.$length(); ++i) {
			Object arg = args.$get(i);
			if (arg instanceof Tag) {
				tag.tag((Tag) arg);
			} else if (arg instanceof DynamicExpr) {
				tag.expr((DynamicExpr) arg);
			} else {
				tag.text((String) arg);
			}
		}
		return tag;
	}

	protected static DynamicExpr realExpr(String location, String exprText, Object value) {
		return new DynamicExpr(value, exprText);
	}

	static {
		$properties(HTML.class).$put("expr", $properties(HTML.class).$get("realExpr"));
		//$properties(HTMLBridge.class).$put("expr", $properties(HTML.class).$get("realExpr"));
	}
}

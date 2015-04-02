package org.steel.html;

import static org.stjs.javascript.JSCollections.$array;

import org.steel.css.CSSRule;
import org.steel.model.DynExpr;
import org.stjs.javascript.Array;
import org.stjs.javascript.functions.Function0;

//list = ["a", "abbr", "address", "area", "article", "aside", "audio", "b", "base", "bdi", "bdo", "big", "blockquote", "body", "br", "button", "canvas", "caption", "cite", "code", "col", "colgroup", "data", "datalist", "dd", "del", "details", "dfn", "dialog", "div", "dl", "dt", "em", "embed", "fieldset", "figcaption", "figure", "footer", "form", "h1", "h2", "h3", "h4", "h5", "h6", "head", "header", "hr", "html", "i", "iframe", "img", "input", "ins", "kbd", "keygen", "label", "legend", "li", "link", "main", "map", "mark", "menu", "menuitem", "meta", "meter", "nav", "noscript", "object", "ol", "optgroup", "option", "output", "p", "param", "picture", "pre", "progress", "q", "rp", "rt", "ruby", "s", "samp", "script", "section", "select", "small", "source", "span", "strong", "style", "sub", "summary", "sup", "table", "tbody", "td", "textarea", "tfoot", "th", "thead", "time", "title", "tr", "track", "u", "ul", "var", "video", "wbr",
public class HTML {
	/**
	 * usage: [cssRule], [tags, text, expr]*
	 * @param arguments
	 * @return
	 */
	public static <T extends Tag<T>> T div(CSSRule css) {
		return tag("div", css);
	}

	public static <T extends Tag<T>> T p(CSSRule css) {
		return tag("p", css);
	}

	public static <T extends Tag<T>> T span(CSSRule css) {
		return tag("span", css);
	}

	public static <T extends Tag<T>> T header(CSSRule css) {
		return tag("header", css);
	}

	public static <T extends Tag<T>> T section(CSSRule css) {
		return tag("section", css);
	}

	public static <T extends Tag<T>> T footer(CSSRule css) {
		return tag("footer", css);
	}

	public static <T extends Tag<T>> T label(CSSRule css) {
		return tag("label", css);
	}

	public static <T extends Tag<T>> T ul(CSSRule css) {
		return tag("ul", css);
	}

	public static <T extends Tag<T>> T ol(CSSRule css) {
		return tag("ol", css);
	}

	public static <T extends Tag<T>> T li(CSSRule css) {
		return tag("li", css);
	}

	public static <T extends Tag<T>> T h1(CSSRule css) {
		return tag("h1", css);
	}

	public static <T extends Tag<T>> T h2(CSSRule css) {
		return tag("h2", css);
	}

	public static <T extends Tag<T>> T h3(CSSRule css) {
		return tag("h3", css);
	}

	public static <T extends Tag<T>> T h4(CSSRule css) {
		return tag("h4", css);
	}

	public static <T extends Tag<T>> T h5(CSSRule css) {
		return tag("h5", css);
	}

	public static <T extends Tag<T>> T h6(CSSRule css) {
		return tag("h6", css);
	}

	@SuppressWarnings("unchecked")
	public static <T extends AnchorTag<T>> T a(CSSRule css) {
		return (T) new AnchorTag<T>("a", css);
	}

	@SuppressWarnings("unchecked")
	public static <T extends InputTag<T>> T input(CSSRule css) {
		return (T) new InputTag<T>("input", css);
	}

	private static Array<Object> add(String param, Array<Object> args) {
		Array<Object> ret = $array(param);

		return ret;
	}

	//	public static Tag div2(CSSRule css) {
	//		return tag("div", $array());
	//	}

	public static <T> DynExpr<T> expr(Function0<T> e) {
		return DynExpr.of(e);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Tag<T>> T tag(String tagName, CSSRule css) {
		return (T) new Tag<T>(tagName, css);
		//console.info("TAG", args);
		//		Tag tag = new Tag(tagName);
		//		for (int i = 0; i < args.$length(); ++i) {
		//			Object arg = args.$get(i);
		//			if (arg instanceof Tag) {
		//				tag.tag((Tag) arg);
		//			} else if (arg instanceof DynExpr) {
		//				tag.expr((DynExpr<?>) arg);
		//			} else {
		//				tag.text((String) arg);
		//			}
		//		}
		//		return tag;
	}

}

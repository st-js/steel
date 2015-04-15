package org.steel.html;

import org.steel.model.DynExpr;
import org.stjs.javascript.functions.Function0;

//list = ["a", "abbr", "address", "area", "article", "aside", "audio", "b", "base", "bdi", "bdo", "big", "blockquote", "body", "br", "button", "canvas", "caption", "cite", "code", "col", "colgroup", "data", "datalist", "dd", "del", "details", "dfn", "dialog", "div", "dl", "dt", "em", "embed", "fieldset", "figcaption", "figure", "footer", "form", "h1", "h2", "h3", "h4", "h5", "h6", "head", "header", "hr", "html", "i", "iframe", "img", "input", "ins", "kbd", "keygen", "label", "legend", "li", "link", "main", "map", "mark", "menu", "menuitem", "meta", "meter", "nav", "noscript", "object", "ol", "optgroup", "option", "output", "p", "param", "picture", "pre", "progress", "q", "rp", "rt", "ruby", "s", "samp", "script", "section", "select", "small", "source", "span", "strong", "style", "sub", "summary", "sup", "table", "tbody", "td", "textarea", "tfoot", "th", "thead", "time", "title", "tr", "track", "u", "ul", "var", "video", "wbr",
public class HTML {
	/**
	 * usage: [cssRule], [tags, text, expr]*
	 * @param arguments
	 * @return
	 */
	public static <T extends HTMLTag<T>> T div() {
		return tag("div");
	}

	public static <T extends HTMLTag<T>> T p() {
		return tag("p");
	}

	public static <T extends HTMLTag<T>> T span() {
		return tag("span");
	}

	public static <T extends HTMLTag<T>> T header() {
		return tag("header");
	}

	public static <T extends HTMLTag<T>> T section() {
		return tag("section");
	}

	public static <T extends HTMLTag<T>> T footer() {
		return tag("footer");
	}

	public static <T extends HTMLTag<T>> T label() {
		return tag("label");
	}

	public static <T extends HTMLTag<T>> T ul() {
		return tag("ul");
	}

	public static <T extends HTMLTag<T>> T ol() {
		return tag("ol");
	}

	public static <T extends HTMLTag<T>> T li() {
		return tag("li");
	}

	public static <T extends HTMLTag<T>> T h1() {
		return tag("h1");
	}

	public static <T extends HTMLTag<T>> T h2() {
		return tag("h2");
	}

	public static <T extends HTMLTag<T>> T h3() {
		return tag("h3");
	}

	public static <T extends HTMLTag<T>> T h4() {
		return tag("h4");
	}

	public static <T extends HTMLTag<T>> T h5() {
		return tag("h5");
	}

	public static <T extends HTMLTag<T>> T h6() {
		return tag("h6");
	}

	@SuppressWarnings("unchecked")
	public static <T extends AnchorTag<T>> T a() {
		return (T) new AnchorTag<T>("a");
	}

	@SuppressWarnings("unchecked")
	public static <T extends InputTag<T>> T input() {
		return (T) new InputTag<T>("input");
	}

	@SuppressWarnings("unchecked")
	public static <T extends InputTag<T>> T inputText() {
		return (T) input().type("text");
	}

	@SuppressWarnings("unchecked")
	public static <T extends InputTag<T>> T inputCheckbox() {
		return (T) input().type("checkbox");
	}

	@SuppressWarnings("unchecked")
	public static <T extends InputTag<T>> T inputRadio() {
		return (T) input().type("radio");
	}

	public static <T> DynExpr<T> expr(Function0<T> e) {
		return DynExpr.of(e);
	}

	@SuppressWarnings("unchecked")
	public static <T extends HTMLTag<T>> T tag(String tagName) {
		return (T) new HTMLTag<T>(tagName);
	}

}

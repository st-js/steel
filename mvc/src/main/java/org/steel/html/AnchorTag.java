package org.steel.html;

import org.steel.css.CSSRule;

public class AnchorTag<T extends AnchorTag<T>> extends Tag<T> {
	public AnchorTag(String tagName, CSSRule css) {
		super(tagName, css);
	}

	public T href(String value) {
		return attr("href", value);
	}
}

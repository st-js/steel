package org.steel.html;


public class AnchorTag<T extends AnchorTag<T>> extends HTMLTag<T> {
	public AnchorTag(String tagName) {
		super(tagName);
	}

	public T href(String value) {
		return attr("href", value);
	}
}

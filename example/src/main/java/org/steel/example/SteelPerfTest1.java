package org.steel.example;

import static org.steel.html.HTML.span;
import static org.stjs.javascript.JSCollections.$array;

import org.steel.css.CSSRule;
import org.steel.html.HTMLTag;
import org.stjs.javascript.Array;
import org.stjs.javascript.dom.Element;

public class SteelPerfTest1 extends HTMLTag<SteelPerfTest1> {
	private final Array<String> data;

	public SteelPerfTest1(CSSRule css) {
		super("span");
		css(css);
		data = $array();
	}

	public void clear() {
		data.splice(0, data.$length());
	}

	public void push(String item) {
		data.push(item);
	}

	@Override
	public SteelPerfTest1 appendTo(Element container) {
		html(//
			data, (item, index, array) -> span().text(item), null//
		);
		return super.appendTo(container);
	}
}

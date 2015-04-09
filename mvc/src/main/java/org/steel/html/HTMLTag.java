package org.steel.html;

import org.steel.comp.SteelComponent;
import org.steel.css.CSSRule;
import org.stjs.javascript.dom.DOMEvent;
import org.stjs.javascript.functions.Callback0;
import org.stjs.javascript.functions.CallbackOrFunction;
import org.stjs.javascript.functions.Function1;

public class HTMLTag<T extends HTMLTag<T>> extends SteelComponent<T> {
	public HTMLTag(String tagName, CSSRule css) {
		super(tagName, css);
	}

	public T id(String value) {
		return attr("id", value);
	}

	public T $for(String value) {
		return attr("for", value);
	}

	public native T onchange(Function1<DOMEvent, Boolean> handler);

	public native T onchange(Callback0 handler);

	protected T onchange(CallbackOrFunction handler) {
		return event("onchange", handler);
	}

	public native T onblur(Function1<DOMEvent, Boolean> handler);

	public native T onblur(Callback0 handler);

	protected T onblur(CallbackOrFunction handler) {
		return event("onblur", handler);
	}

	public native T onclick(Function1<DOMEvent, Boolean> handler);

	public native T onclick(Callback0 handler);

	protected T onclick(CallbackOrFunction handler) {
		return event("onclick", handler);
	}

	public native T ondblclick(Function1<DOMEvent, Boolean> handler);

	public native T ondblclick(Callback0 handler);

	protected T ondblclick(CallbackOrFunction handler) {
		return event("ondblclick", handler);
	}

	public native T onkeypress(Function1<DOMEvent, Boolean> handler);

	public native T onkeypress(Callback0 handler);

	protected T onkeypress(CallbackOrFunction handler) {
		return event("onkeypress", handler);
	}

	public native T onkeydown(Function1<DOMEvent, Boolean> handler);

	public native T onkeydown(Callback0 handler);

	protected T onkeydown(CallbackOrFunction handler) {
		return event("onkeydown", handler);
	}

	public native T onkeyup(Function1<DOMEvent, Boolean> handler);

	public native T onkeyup(Callback0 handler);

	protected T onkeyup(CallbackOrFunction handler) {
		return event("onkeyup", handler);
	}

}

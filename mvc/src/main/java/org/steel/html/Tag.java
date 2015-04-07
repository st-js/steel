package org.steel.html;

import static org.stjs.javascript.Global.window;
import static org.stjs.javascript.JSCollections.$castArray;
import static org.stjs.javascript.JSObjectAdapter.$properties;

import org.steel.css.CSSRule;
import org.steel.css.CSSStatus;
import org.steel.model.DynExpr;
import org.stjs.javascript.Array;
import org.stjs.javascript.JSGlobal;
import org.stjs.javascript.dom.DOMEvent;
import org.stjs.javascript.dom.Element;
import org.stjs.javascript.dom.Text;
import org.stjs.javascript.functions.Callback0;
import org.stjs.javascript.functions.CallbackOrFunction;
import org.stjs.javascript.functions.Function0;
import org.stjs.javascript.functions.Function1;
import org.stjs.javascript.functions.Function3;

public class Tag<T extends Tag<T>> {
	public CSSRule cssRule;
	private Element domElement;

	public Tag(String tagName, CSSRule css) {
		domElement = window.document.createElement(tagName);
		this.cssRule = css;
		if (cssRule != null) {
			domElement.className = cssRule.name;
		}
	}

	public Element element() {
		return domElement;
	}

	public CSSRule getCssRule() {
		return cssRule;
	}

	public T rule(CSSStatus rule, Function0<Boolean> activate) {
		DynamicCSSRule dynRule = new DynamicCSSRule(rule, DynExpr.of(activate));
		dynRule.setTo(domElement);
		return castThis();
	}

	@SuppressWarnings("unchecked")
	protected T castThis() {
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T attr(String name, Object value) {
		if (JSGlobal.typeof(value) == "function") {
			return dynAttr(name, (Function0<String>) value);
		}
		domElement.setAttribute(name, (String) value);
		return castThis();
	}

	public T dynAttr(String name, Function0<String> value) {
		DynamicAttribute attr = new DynamicAttribute(name, DynExpr.of(value));
		attr.setTo(domElement);
		return castThis();
	}

	public T id(String value) {
		return attr("id", value);
	}

	public T $for(String value) {
		return attr("for", value);
	}

	native public T text(String text);

	native public T text(Function0<String> expr);

	@SuppressWarnings("unchecked")
	protected T text(Object text) {
		if (JSGlobal.typeof(text) == "function") {
			DynamicTextNode dynText = new DynamicTextNode(DynExpr.of((Function0<String>) text));
			dynText.appendTo(domElement);
			return castThis();
		}
		Text textNode = window.document.createTextNode(text != null ? text.toString() : "");
		domElement.appendChild(textNode);
		return castThis();
	}

	public native T html(Tag<?>... arguments);

	public native <V> T html(Array<V> array, Function3<V, Integer, Array<V>, Tag<?>> eachItemCallback, Function0<Tag<?>> emptyArrayCallback);

	@SuppressWarnings({"rawtypes", "unchecked"})
	protected T html(Object... arguments) {
		Array<Object> args = $castArray(arguments);
		if (args.$length() == 3 && JSGlobal.typeof(args.$get(1)) == "function") {
			return (T) dynamicHtml((Array) args.$get(0), (Function3) args.$get(1), (Function0) args.$get(2));
		}
		return staticHtml((Array) $castArray(arguments));
	}

	protected T staticHtml(Array<Tag<?>> tags) {
		for (int i = 0; i < tags.$length(); ++i) {
			tags.$get(i).appendTo(domElement);
		}
		return castThis();
	}

	protected <V> T dynamicHtml(Array<V> array, Function3<V, Integer, Array<V>, Tag<?>> eachItemCallback, Function0<Tag<?>> emptyArrayCallback) {
		DynamicElementList<V> dynList = new DynamicElementList<V>(array, eachItemCallback, emptyArrayCallback);
		dynList.appendTo(domElement);
		return castThis();
	}

	public T appendTo(Element container) {
		container.appendChild(domElement);
		return castThis();
	}

	public native T event(String name, Function1<DOMEvent, Boolean> handler);

	public native T event(String name, Callback0 handler);

	protected T event(String name, CallbackOrFunction handler) {
		$properties(domElement).$put(name, handler);
		return castThis();
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

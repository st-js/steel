package org.steel.comp;

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

public class SteelComponent<T extends SteelComponent<T>> {
	public CSSRule cssRule;
	protected Element domElement;

	public SteelComponent(String tagName) {
		domElement = window.document.createElement(tagName);
	}

	public Element element() {
		return domElement;
	}

	public CSSRule getCssRule() {
		return cssRule;
	}

	public native T css(CSSRule css);

	public native T css(CSSStatus rule, Function0<Boolean> activate);

	private T css(Object param1, Object param2) {
		if (param1 == null) {
			return castThis();
		}
		if (param1 instanceof CSSRule) {
			return internalCss1((CSSRule) param1);
		}
		return internalCss2((CSSStatus) param1, (Function0<Boolean>) param2);
	}

	private T internalCss1(CSSRule css) {
		this.cssRule = css;
		if (cssRule != null) {
			domElement.className = cssRule.name;
		}
		return castThis();
	}

	private T internalCss2(CSSStatus rule, Function0<Boolean> activate) {
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

	public native T html(SteelComponent<?>... arguments);

	public native <V> T html(Array<V> array, Function3<V, Integer, Array<V>, SteelComponent<?>> eachItemCallback,
			Function0<SteelComponent<?>> emptyArrayCallback);

	@SuppressWarnings({"rawtypes", "unchecked"})
	protected T html(Object... arguments) {
		Array<Object> args = $castArray(arguments);
		if (args.$length() == 3 && JSGlobal.typeof(args.$get(1)) == "function") {
			return (T) dynamicHtml((Array) args.$get(0), (Function3) args.$get(1), (Function0) args.$get(2));
		}
		Object ret = staticHtml((Array) args);
		return (T) ret;
	}

	protected T staticHtml(Array<SteelComponent<?>> tags) {
		for (int i = 0; i < tags.$length(); ++i) {
			tags.$get(i).appendTo(domElement);
		}
		return castThis();
	}

	protected <V> T dynamicHtml(Array<V> array, Function3<V, Integer, Array<V>, SteelComponent<?>> eachItemCallback,
			Function0<SteelComponent<?>> emptyArrayCallback) {
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

}

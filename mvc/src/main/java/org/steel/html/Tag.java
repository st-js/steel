package org.steel.html;

import static org.stjs.javascript.Global.window;
import static org.stjs.javascript.JSCollections.$array;
import static org.stjs.javascript.JSCollections.$castArray;
import static org.stjs.javascript.JSCollections.$map;
import static org.stjs.javascript.JSObjectAdapter.$properties;

import org.steel.css.CSSRule;
import org.steel.model.DynExpr;
import org.stjs.javascript.Array;
import org.stjs.javascript.JSGlobal;
import org.stjs.javascript.Map;
import org.stjs.javascript.dom.DOMEvent;
import org.stjs.javascript.dom.Element;
import org.stjs.javascript.dom.Text;
import org.stjs.javascript.functions.Callback1;
import org.stjs.javascript.functions.Function0;
import org.stjs.javascript.functions.Function1;
import org.stjs.javascript.functions.Function3;
import org.stjs.javascript.jquery.JQueryCore;

public class Tag<T extends Tag<T>> {
	public JQueryCore<?> $;
	public final String tagName;
	private Array<Object> children;
	private Array<DynExpr<?>> expressions;
	private Object rootContext;
	public CSSRule cssRule;

	private Map<String, Object> attrs;
	private Map<String, Function1<DOMEvent, Boolean>> events;
	private Element element;

	public Tag(String tagName, CSSRule css) {
		this.tagName = tagName;
		this.cssRule = css;
		attrs = $map();
		events = $map();
		children = $array();
		expressions = $array();
	}

	public CSSRule getCssRule() {
		return cssRule;
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
		attrs.$put(name, value);
		return castThis();
	}

	public T dynAttr(String name, Function0<String> value) {
		attrs.$put(name, new DynamicAttribute(name, DynExpr.of(value)));
		return castThis();
	}

	public T id(String value) {
		return attr("id", value);
	}

	public T $for(String value) {
		return attr("for", value);
	}

	private void forTags(Callback1<Tag> callback) {
		children.$forEach(child -> {
			if (child instanceof Tag) {
				callback.$invoke((Tag) child);
			}
		});
	}

	native public T text(String text);

	native public T text(Function0<String> expr);

	@SuppressWarnings("unchecked")
	protected T text(Object text) {
		if (JSGlobal.typeof(text) == "function") {
			children.push(new DynamicTextNode(DynExpr.of((Function0<String>) text)));
			return castThis();
		}
		children.push(text != null ? text.toString() : "");
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
			children.push(tags.$get(i));
		}
		return castThis();
	}

	protected <V> T dynamicHtml(Array<V> array, Function3<V, Integer, Array<V>, Tag<?>> eachItemCallback, Function0<Tag<?>> emptyArrayCallback) {
		children.push(new DynamicElementList<V>(array, eachItemCallback, emptyArrayCallback));
		return castThis();
	}

	public T appendTo(Element container) {
		element = window.document.createElement(tagName);
		for (String att : attrs) {
			Object value = attrs.$get(att);
			if (value instanceof DynamicAttribute) {
				((DynamicAttribute) value).setTo(element);
			} else {
				element.setAttribute(att, (String) value);
			}
		}

		for (String ev : events) {
			$properties(element).$put(ev, events.$get(ev));
		}

		children.$forEach(child -> {
			if (child == null) {
				return;
			}
			if (child instanceof Tag) {
				((Tag<?>) child).appendTo(element);
			} else if (child instanceof DynamicTextNode) {
				((DynamicTextNode) child).appendTo(element);
			} else if (child instanceof DynamicElementList) {
				((DynamicElementList<?>) child).appendTo(element);
			} else {
				Text textNode = window.document.createTextNode((String) child);
				element.appendChild(textNode);
			}
		});

		container.appendChild(element);

		return castThis();
	}

	public T event(String name, Function1<DOMEvent, Boolean> handler) {
		events.$put(name, handler);
		return castThis();
	}

	public T onchange(Function1<DOMEvent, Boolean> handler) {
		return event("onchange", handler);
	}
}

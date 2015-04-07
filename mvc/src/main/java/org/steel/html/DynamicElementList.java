package org.steel.html;

import static org.stjs.javascript.JSObjectAdapter.$properties;

import org.steel.bridge.ArrayObserver;
import org.steel.bridge.ArraySplice;
import org.stjs.javascript.Array;
import org.stjs.javascript.dom.Element;
import org.stjs.javascript.dom.Node;
import org.stjs.javascript.functions.Function0;
import org.stjs.javascript.functions.Function3;

public class DynamicElementList<V> {
	private final Array<V> array;
	private final Function3<V, Integer, Array<V>, Tag<?>> eachItemCallback;
	private final Function0<Tag<?>> emptyArrayCallback;
	private Element container;
	private Element emptyListChild;

	public DynamicElementList(Array<V> array, Function3<V, Integer, Array<V>, Tag<?>> eachItemCallback, Function0<Tag<?>> emptyArrayCallback) {
		this.array = array;
		this.eachItemCallback = eachItemCallback;
		this.emptyArrayCallback = emptyArrayCallback;
		new ArrayObserver(array).open(this::arrayModified);
	}

	public void appendTo(Element aContainer) {
		this.container = aContainer;
		//TODO - mark down also the index where the children of this list start. note that this can move as other dynamic list may also be attached to the same container
		renderChildren(0, array.$length());
	}

	private void removeAllChildren() {
		Node child;
		do {
			child = (Node) $properties(container).$get("firstChild");
			if (child != null) {
				container.removeChild(child);
			}
		}
		while (child != null);
	}

	public void removeChildren(int from, int to) {
		for (int i = from; i < to; i++) {
			//TODO - mark down also the index
			container.removeChild(container.childNodes.$get(i));
			//TODO - stop observers!
		}
	}

	public void renderChildren(int from, int to) {
		if (array.$length() == 0) {
			removeAllChildren();

			if (emptyArrayCallback != null) {
				Tag<?> child = emptyArrayCallback.$invoke();
				if (child != null) {
					emptyListChild = child.element();
					child.appendTo(container);
				}
			}
			return;
		}
		if (emptyListChild != null) {
			container.removeChild(emptyListChild);
			emptyListChild = null;
		}
		//several elements
		for (int i = from; i < to; i++) {
			//TODO append to the right position
			Tag<?> child = eachItemCallback.$invoke(array.$get(i), i, array);
			if (child != null) {
				child.appendTo(container);
			}
		}
	}

	private void arrayModified(Array<ArraySplice> splices) {
		//TODO handle delete
		splices.$forEach(splice -> {
			removeChildren(splice.index, splice.index + splice.removed.$length());
			renderChildren(splice.index, splice.index + splice.addedCount);
		});
	}

}

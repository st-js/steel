package org.steel.bridge;

import org.stjs.javascript.Array;
import org.stjs.javascript.annotation.STJSBridge;
import org.stjs.javascript.functions.Callback1;

@STJSBridge
public class ArrayObserver {
	public ArrayObserver(Array<?> array) {
		//
	}

	public native void open(Callback1<Array<ArraySplice>> splices);

}

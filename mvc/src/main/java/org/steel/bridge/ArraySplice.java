package org.steel.bridge;

import org.stjs.javascript.Array;
import org.stjs.javascript.annotation.STJSBridge;
import org.stjs.javascript.annotation.SyntheticType;

@STJSBridge
@SyntheticType
public class ArraySplice {
	public int index;
	public Array<?> removed;
	public int addedCount;
}

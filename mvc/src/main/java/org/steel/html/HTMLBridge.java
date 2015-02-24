package org.steel.html;

import org.steel.model.DynamicExpr;
import org.stjs.javascript.annotation.STJSBridge;
import org.stjs.javascript.annotation.Template;

@STJSBridge
public class HTMLBridge {
	@Template("assert")
	public static native DynamicExpr expr(Object expr);
}

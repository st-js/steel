package org.steel.css;

import static org.stjs.javascript.Global.window;
import static org.stjs.javascript.JSCollections.$map;
import static org.stjs.javascript.JSObjectAdapter.$properties;

import org.stjs.javascript.Array;
import org.stjs.javascript.Global;
import org.stjs.javascript.JSStringAdapter;
import org.stjs.javascript.Map;
import org.stjs.javascript.RegExp;
import org.stjs.javascript.StyleSheet;
import org.stjs.javascript.dom.Element;

public class CSSStyleSheet {
	private StyleSheet stylesheet;

	public void addRule(CSSRule cssRule) {
		createStyleSheet();

		addRuleWithPath("", cssRule, false);
	}

	public void addRuleWithPath(String parentSelector, CSSRule cssRule, boolean isSubRule) {
		@SuppressWarnings("unchecked")
		String selector = parentSelector + (isSubRule ? "" : " ") + cssRule.selector;

		String rules = "";
		for (String prop : $properties(cssRule)) {
			if (CSSRule.RESERVED_PROPS.$get(prop)) {
				continue;
			}
			Object value = $properties(cssRule).$get(prop);
			if (value == null) {
				continue;
			}
			if (Global.typeof(value) == "function") {
				continue;
			}
			if (value instanceof CSSRule) {
				addRuleWithPath(selector, (CSSRule) value, false);
				continue;
			}

			rules += dashed(prop) + ":" + value + ";";
		}
		if (rules != "") {
			addRuleAndSelector(selector, rules);
		}

		//add the subrules
		for (String r : cssRule.subrules) {
			addRuleWithPath(selector, cssRule.subrules.$get(r), true);
		}
	}

	private void addRuleAndSelector(String selector, String rules) {
		//console.info("ADD:" + selector + "=", rules);
		if ($properties(stylesheet).$get("insertRule") != null) {
			stylesheet.insertRule(selector + "{" + rules + "}", 0);
		} else {
			stylesheet.addRule(selector, rules, 0);
		}
	}

	@SuppressWarnings("unchecked")
	private String dashed(String prop) {
		return JSStringAdapter.replace(prop, new RegExp("[^_A-Z]([A-Z])"), (x) -> ((Array<String>) (Object) x).$get(0) + "-"
				+ ((Array<String>) (Object) x).$get(1).toLowerCase());
	}

	private Map<String, String> baseRules;

	public String toSass(String parentSelector, CSSRule cssRule, boolean isSubRule) {
		if (baseRules == null) {
			baseRules = $map();
		}

		String selector = parentSelector + (isSubRule ? "" : " ") + cssRule.selector;

		String ownRules = "";
		String childRules = "";
		for (String prop : $properties(cssRule)) {
			if (CSSRule.RESERVED_PROPS.$get(prop)) {
				continue;
			}
			Object value = $properties(cssRule).$get(prop);
			if (value == null) {
				continue;
			}
			if (Global.typeof(value) == "function") {
				continue;
			}
			if (value instanceof CSSRule) {
				childRules += toSass(selector, (CSSRule) value, false);
				continue;
			}

			ownRules += dashed(prop) + ":" + value + ";";
		}

		//add the subrules
		for (String r : cssRule.subrules) {
			childRules += toSass(selector, cssRule.subrules.$get(r), true);
		}

		if (ownRules != "") {
			String prevRules = baseRules.$get(cssRule.selector);
			if (prevRules == null) {
				baseRules.$put(cssRule.selector, ownRules);
			} else if (ownRules == prevRules) {
				ownRules = "";
			}
		}

		if (childRules == "" && ownRules == "") {
			return "";
		}

		return (isSubRule ? "&" : "") + cssRule.selector + " {" + ownRules + " " + childRules + "}";
	}

	private void createStyleSheet() {
		if (stylesheet == null) {
			Element stylesheetElement = window.document.createElement("style");
			stylesheetElement.appendChild(window.document.createTextNode(""));
			window.document.getElementsByTagName("head").$get(0).appendChild(stylesheetElement);
			stylesheet = stylesheetElement.sheet;
		}
	}

}

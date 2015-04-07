package org.steel.css;

import static org.stjs.javascript.JSCollections.$map;
import static org.stjs.javascript.JSObjectAdapter.$properties;

import org.stjs.javascript.Global;
import org.stjs.javascript.JSStringAdapter;
import org.stjs.javascript.Map;
import org.stjs.javascript.RegExp;
import org.stjs.javascript.functions.Function1;

public class CSSRule {
	public static final Map<String, Boolean> RESERVED_PROPS = $map("name", true, "selector", true, "subrules", true);

	public final String name;
	public final String selector;

	/**
	 * supplementary rules for different statuses
	 */
	public final Map<String, CSSRule> subrules;

	public final Map<String, CSSStatus> statuses;

	public String font;
	public String width;
	public String height;
	public String top;
	public String right;
	public String bottom;
	public String left;

	public String background;
	public String color;
	public String textAlign;

	public String whiteSpace;

	public String overflow;

	public String position;
	public String display;
	public String backgroundColor;
	public String cursor;
	public String border;
	public String padding;
	public String boxShadow;

	public String zIndex;
	public String overflowX;
	public String overflowY;

	public String marginRight;
	public String marginTop;
	public String marginLeft;
	public String marginBottom;
	public String fontSize;
	public String borderStyle;
	public String borderWidth;
	public String borderColor;
	public String borderBottom;
	public String lineHeight;
	public String textDecoration;

	public CSSStatus subruleStatus;

	public String backgroundPosition;

	public String margin;

	public String listStyle;

	public String fontWeight;

	public String fontFamily;

	public String outline;
	public String borderRadius;

	public CSSRule(String selector) {//
		this.selector = selector;
		this.name = selector.indexOf(".") == 0 ? selector.substring(1) : selector;
		this.subrules = $map();
		this.statuses = $map();
	}

	public native CSSStatus status(String statusName);

	public CSSStatus status(String statusName, String statusGroup) {
		CSSStatus status = statuses.$get(statusName);
		if (status == null) {
			status = new CSSStatus(this, statusName, statusGroup);
			statuses.$put(statusName, status);
		}
		return status;
	}

	/**
	 * @param subRuleName
	 * @return a subrule with the given name (without the ".", or starting with ":" like ":hover")
	 */
	public CSSRule when(CSSStatus status) {
		CSSRule subrule = subrules.$get("" + status.$id);
		if (subrule == null) {
			subrule = createSubrule(status);
			subrules.$put("" + status.$id, subrule);
		}
		return subrule;
	}

	/**
	 * @param subRuleName
	 * @return a new subrule with the given name
	 */
	public CSSRule createSubrule(CSSStatus status) {
		String sel = status.name.startsWith(":") ? status.name : "." + status.name;
		CSSRule rule = new CSSRule(sel);
		rule.subruleStatus = status;
		return rule;
	}

	public static CSSRule div() {
		return new CSSRule("div");
	}

	public static CSSRule span() {
		return new CSSRule("div");
	}

	public static CSSRule p() {
		return new CSSRule("div");
	}

	public CSSRule absolute(String top, String left, String bottom, String right) {
		position = "absolute";
		this.bottom = bottom;
		this.top = top;
		this.left = left;
		this.right = right;
		return this;
	}

	public CSSRule relative(String top, String left, String bottom, String right) {
		position = "relative";
		this.bottom = bottom;
		this.top = top;
		this.left = left;
		this.right = right;
		return this;
	}

	public CSSRule size(String width, String height) {
		this.width = width;
		this.height = height;
		return this;
	}

	public int val(Object prop) {
		if (prop == null) {
			return 0;
		}
		return Global.parseInt(JSStringAdapter.replace(prop + "", new RegExp("[^0-9.-]", "g"), ""));
	}

	/**
	 * make an aritmetic calculation on the property and puts it back to pixels. Example. px("5px", x -> x + 2) returns "7px"
	 * @param
	 * @return
	 */
	public String px(Object value, Function1<Integer, Integer> transform) {
		return transform.$invoke(val(value)) + "px";
	}

	public CSSStatus active() {
		return status(":active");
	}

	public CSSStatus visited() {
		return status(":visited");
	}

	public CSSStatus hover() {
		return status(":hover");
	}

	public CSSStatus focus() {
		return status(":focus");
	}

	public CSSStatus before() {
		return status(":before");
	}

	public CSSStatus after() {
		return status(":after");
	}

	/**
	 * copy recursively the rules from other to this rule (only the non-null fields).
	 * @param other
	 * @return this
	 */
	public CSSRule mixin(CSSRule other) {
		for (String prop : $properties(other)) {
			if (RESERVED_PROPS.$get(prop)) {
				continue;
			}
			Object value = $properties(other).$get(prop);
			if (value == null) {
				continue;
			}
			if (Global.typeof(value) == "function") {
				continue;
			}
			if (value instanceof CSSRule) {
				//propagate down to children having the same name
				CSSRule thisField = (CSSRule) $properties(this).$get(prop);
				if (thisField != null) {
					thisField.mixin((CSSRule) value);
				}
				continue;
			}
			//			if (value instanceof CSSRuleGroup) {
			//				addRuleGroupWithPath(selector, (CSSRuleGroup) value, false);
			//				continue;
			//			}

			$properties(this).$put(prop, value);
		}
		return this;
	}

}

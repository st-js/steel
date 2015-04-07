package org.steel.css;

public class CSSStatus {
	private static int ids = 0;
	public final int $id;

	public final CSSRule rule;
	public final String name;
	public final String group;

	public CSSStatus(CSSRule rule, String name, String group) {
		this.rule = rule;
		this.name = name;
		this.group = group;
		this.$id = ids++;
	}

}

package org.steel.html;

import org.steel.css.CSSRule;
import org.stjs.javascript.annotation.Native;
import org.stjs.javascript.functions.Callback1;

public interface SteelComponent {
	SteelComponent show();

	SteelComponent hide();

	SteelComponent onParentShown(Callback1<SteelComponent> callback);

	SteelComponent onParentHidden(Callback1<SteelComponent> callback);

	SteelComponent onDetach(Callback1<SteelComponent> callback);

	SteelComponent inject(Callback1<SteelComponent> callback);

	SteelComponent appendTo(SteelComponent parent);

	@Native
	CSSRule css();

	SteelComponent css(CSSRule css);

}

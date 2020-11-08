package work.lclpnet.corebase.util;

import net.minecraft.util.text.Color;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;

public class TextComponentHelper {

	public static final Style resetStyleNoColor = Style.EMPTY
			.setBold(false) // bold
			.setItalic(false) // italic
			.setUnderlined(false) // underlined
			.setStrikethrough(false) // strikethrough
			.setObfuscated(false); // obfuscated
	
	public static IFormattableTextComponent setStyleColor(IFormattableTextComponent component, Color c) {
		return component.setStyle(component.getStyle().setColor(c));
	}
	
	public static boolean hasFormatting(ITextComponent component) {
		Style style = component.getStyle();
		return style.getColor() != null || 
				style.getBold() ||
				style.getItalic() ||
				style.getObfuscated() ||
				style.getStrikethrough() ||
				style.getUnderlined() ||
				style.getClickEvent() != null ||
				style.getHoverEvent() != null ||
				style.getInsertion() != null;
	}
	
	public static boolean hasDeepFormatting(IFormattableTextComponent component) {
		return hasFormatting(component) || component.getSiblings().stream().anyMatch(TextComponentHelper::hasFormatting);
	}
	
}

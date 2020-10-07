package work.lclpnet.corebase.util;

import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;

public class TextComponentHelper {

	/**
	 * Method redirect to {@link IFormattableTextComponent#func_240702_b_(String)}
	 */
	public static IFormattableTextComponent appendText(IFormattableTextComponent component, String text) {
		return component.func_240702_b_(text);
	}
	
	/**
	 * Method redirect to {@link IFormattableTextComponent#func_240699_a_(TextFormatting)}
	 */
	public static IFormattableTextComponent applyTextStyle(IFormattableTextComponent component, TextFormatting formatting) {
		return component.func_240699_a_(formatting);
	}
	
	/**
	 * Method redirect to {@link IFormattableTextComponent#func_240701_a_(TextFormatting...)}
	 */
	public static IFormattableTextComponent applyTextStyles(IFormattableTextComponent component, TextFormatting... formattings) {
		return component.func_240701_a_(formattings);
	}
	
	/**
	 * Method redirect to {@link IFormattableTextComponent#func_230529_a_(ITextComponent)}
	 */
	public static IFormattableTextComponent appendSibling(IFormattableTextComponent component, ITextComponent sibling) {
		return component.func_230529_a_(sibling);
	}
	
	public static boolean hasFormatting(ITextComponent component) {
		Style style = component.getStyle();
		return style.func_240711_a_() != null || 
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
		return hasFormatting(component) && component.getSiblings().stream().allMatch(TextComponentHelper::hasFormatting);
	}
	
}

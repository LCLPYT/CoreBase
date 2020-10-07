package work.lclpnet.corebase.util;

import net.minecraft.util.text.Color;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;

public class TextComponentHelper {

	public static final Style resetStyleNoColor = Style.field_240709_b_
			.func_240713_a_(false) // bold
			.func_240722_b_(false) // italic
			.setUnderlined(false) // underlined
			.setStrikethrough(false) // strikethrough
			.setObfuscated(false); // obfuscated
	
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
	
	/**
	 * Method redirect to {@link IFormattableTextComponent#func_230530_a_(Style)}
	 */
	public static IFormattableTextComponent setStyle(IFormattableTextComponent component, Style style) {
		return component.func_230530_a_(style);
	}
	
	public static IFormattableTextComponent setStyleColor(IFormattableTextComponent component, Color c) {
		return setStyle(component, component.getStyle().func_240718_a_(c));
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

package work.lclpnet.corebase.util;

import net.minecraft.util.text.Color;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;

public class TextComponentHelper {

	public static final Style resetStyleNoColor = Style.EMPTY
			.setBold(false) // bold
			.setItalic(false) // italic
			.setUnderlined(false) // underlined
			.setStrikethrough(false) // strikethrough
			.setObfuscated(false); // obfuscated
	
	/**
	 * Method redirect to {@link IFormattableTextComponent#func_240702_b_(String)}
	 */
	@Deprecated
	public static IFormattableTextComponent appendText(IFormattableTextComponent component, String text) {
		return component.appendString(text);
	}
	
	/**
	 * Method redirect to {@link IFormattableTextComponent#func_240699_a_(TextFormatting)}
	 */
	@Deprecated
	public static IFormattableTextComponent applyTextStyle(IFormattableTextComponent component, TextFormatting formatting) {
		return component.mergeStyle(formatting);
	}
	
	/**
	 * Method redirect to {@link IFormattableTextComponent#func_240701_a_(TextFormatting...)}
	 */
	@Deprecated
	public static IFormattableTextComponent applyTextStyles(IFormattableTextComponent component, TextFormatting... formattings) {
		return component.mergeStyle(formattings);
	}
	
	/**
	 * Method redirect to {@link IFormattableTextComponent#func_230529_a_(ITextComponent)}
	 */
	@Deprecated
	public static IFormattableTextComponent appendSibling(IFormattableTextComponent component, ITextComponent sibling) {
		return component.append(sibling);
	}
	
	/**
	 * Method redirect to {@link IFormattableTextComponent#func_230530_a_(Style)}
	 */
	@Deprecated
	public static IFormattableTextComponent setStyle(IFormattableTextComponent component, Style style) {
		return component.setStyle(style);
	}
	
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

package work.lclpnet.corebase.asm.mixin.common;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponent;

@Deprecated
@Mixin(TextComponent.class)
public abstract class MixinTextComponent implements ITextComponent{

	/*@Override
	public ITextComponent applyTextStyle(TextFormatting color) {
		ITextComponent applied = ITextComponent.super.applyTextStyle(color);
		Style style = applied.getStyle();
		
		if(color == TextFormatting.RESET) {
			style.setBold(false);
			style.setItalic(false);
			style.setObfuscated(false);
			style.setStrikethrough(false);
			style.setUnderlined(false);
			style.setColor(null);
		}
		
		applied.setStyle(style);
		
		return applied;
	}*/

}

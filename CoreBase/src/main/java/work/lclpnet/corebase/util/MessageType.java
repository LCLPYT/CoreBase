package work.lclpnet.corebase.util;

import java.util.function.Function;

import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;

public class MessageType {

	public static final MessageType INFO = new MessageType(c -> TextComponentHelper.applyTextStyle(c, TextFormatting.GRAY)),
			ERROR = new MessageType(c -> TextComponentHelper.applyTextStyle(c, TextFormatting.RED)),
			SUCCESS = new MessageType(c -> TextComponentHelper.applyTextStyle(c, TextFormatting.GREEN)),
			OTHER = new MessageType(c -> TextComponentHelper.applyTextStyle(c, TextFormatting.AQUA));
	
	private Function<IFormattableTextComponent, ITextComponent> apply;
	
	public MessageType(Function<IFormattableTextComponent, ITextComponent> apply) {
		this.apply = apply;
	}
	
	public Function<IFormattableTextComponent, ITextComponent> getApply() {
		return apply;
	}
	
	public void setApply(Function<IFormattableTextComponent, ITextComponent> apply) {
		this.apply = apply;
	}
	
	public ITextComponent apply(IFormattableTextComponent component) {
		return apply.apply(component);
	}
	
}

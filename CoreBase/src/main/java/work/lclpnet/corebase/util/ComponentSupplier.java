package work.lclpnet.corebase.util;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class ComponentSupplier {

	private String prefix;
	
	public ComponentSupplier() {
		this(null);
	}
	
	public ComponentSupplier(String prefix) {
		this.prefix = prefix;
	}
	
	public ITextComponent getPrefix() {
		return prefix != null ? ComponentHelper.getPrefix(prefix) : new StringTextComponent("");
	}
	
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	public ITextComponent message(String msg, MessageType type) {
		return ComponentHelper.message(prefix, msg, type);
	}
	
	public ITextComponent complexMessage(String msg, TextFormatting tf, Substitute... substitutes) {
		return ComponentHelper.complexMessage(prefix, msg, tf, substitutes);
	}
	
	public ITextComponent complexMessage(String msg, TextFormat tf, Substitute... substitutes) {
		return ComponentHelper.complexMessage(prefix, msg, tf, substitutes);
	}
	
	public static class TextFormat {
		protected TextFormatting[] format;
		
		public TextFormat(TextFormatting... format) {
			this.format = format;
		}
		
		public ITextComponent apply(StringTextComponent component) {
			return component.applyTextStyles(format);
		}

		public TextFormatting[] getFormat() {
			return format;
		}
		
		public void setFormat(TextFormatting[] format) {
			this.format = format;
		}
		
	}
	
}

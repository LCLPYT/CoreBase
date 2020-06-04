package work.lclpnet.corebase.util;

import net.minecraft.util.text.TextFormatting;
import work.lclpnet.corebase.util.ComponentSupplier.TextFormat;

public class Substitute extends TextFormat{
	private Object obj;
	
	public Substitute(Object obj, TextFormatting... format) {
		super(format);
		this.obj = obj;
	}
	
	public Object getObj() {
		return obj;
	}
	
	public void setObj(Object obj) {
		this.obj = obj;
	}
	
}
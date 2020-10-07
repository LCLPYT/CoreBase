package work.lclpnet.corebase.util;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class LCLPConstants {

	public static final ITextComponent SEPERATOR = TextComponentHelper.applyTextStyles(new StringTextComponent("============================================="), TextFormatting.DARK_GREEN, TextFormatting.BOLD, TextFormatting.STRIKETHROUGH),
			SEPERATOR_SMALL = TextComponentHelper.applyTextStyles(new StringTextComponent("------------------"), TextFormatting.YELLOW, TextFormatting.STRIKETHROUGH);
	
	public static final String LCLP_UUID = "7357a549-fa3e-4342-91b2-63e5e73ed39a";

}

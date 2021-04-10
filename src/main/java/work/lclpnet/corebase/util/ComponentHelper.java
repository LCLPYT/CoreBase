package work.lclpnet.corebase.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.util.text.Color;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import work.lclpnet.corebase.util.ComponentSupplier.TextFormat;

public class ComponentHelper {

	private static Pattern FORMATTER_PATTERN;
	public static final char COLOR_CHAR = '\u00a7';

	static {
		try {
			Field f = Formatter.class.getDeclaredField("fsPattern");
			boolean b = f.isAccessible();
			f.setAccessible(true);

			Object pattern = f.get(null);

			f.setAccessible(b);

			FORMATTER_PATTERN = (Pattern) pattern;
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
			FORMATTER_PATTERN = Pattern.compile("%(\\d+\\$)?([-#+ 0,(\\<]*)?(\\d+)?(\\.\\d+)?([tT])?([a-zA-Z%])"); //Version of JavaSE-1.8
		}
	}

	public static IFormattableTextComponent message(String prefix, String msg, MessageType type) {
		return getPrefix(prefix).appendSibling(type.apply(new StringTextComponent(msg)));
	}

	public static IFormattableTextComponent getPrefix(String prefix) {
		return new StringTextComponent(prefix + "> ").mergeStyle(TextFormatting.BLUE);
	}

	public static IFormattableTextComponent complexMessage(String prefix, String msg, TextFormatting tf, Substitute... substitutes) {
		return complexMessage(prefix, msg, new TextFormat(tf), substitutes);
	}

	public static IFormattableTextComponent complexMessage(String prefix, String msg, TextFormat tf, Substitute... substitutes) {
		if(FORMATTER_PATTERN == null) return null;

		List<String> matches = new ArrayList<>(); 

		Matcher m = FORMATTER_PATTERN.matcher(msg);
		while(m.find()) matches.add(m.group());

		List<ITextComponent> components = new ArrayList<>();

		String[] split = msg.split(FORMATTER_PATTERN.pattern());
		int i = 0;
		for(String s : split) {
			components.add(tf.apply(new StringTextComponent(s)));
			if(i < matches.size() && i < substitutes.length) {
				if(substitutes[i].getObj() instanceof IFormattableTextComponent) {
					IFormattableTextComponent itc = (IFormattableTextComponent) substitutes[i].getObj();
					if(!TextComponentHelper.hasDeepFormatting(itc))
						itc.mergeStyle(substitutes[i].getFormat());
					components.add(itc);
				} else {
					String formatted = String.format(matches.get(i), substitutes[i].getObj());
					ITextComponent c = (substitutes[i].getFormat().length <= 0 ? tf : substitutes[i]).apply(new StringTextComponent(formatted));
					components.add(c);
				}
			}
			i++;
		}

		IFormattableTextComponent comp = getPrefix(prefix);
		components.forEach(comp::appendSibling);
		return comp;
	}

	public static IFormattableTextComponent convertCharStyleToComponentStyle(ITextComponent itc) {
		return convertCharStyleToComponentStyle(itc.getUnformattedComponentText());
	}

	public static IFormattableTextComponent convertCharStyleToComponentStyle(String text) {
		return convertCharStyleToComponentStyle(text, COLOR_CHAR);
	}

	public static IFormattableTextComponent convertCharStyleToComponentStyle(String text, char colorChar) {
		return convertCharStyleToComponentStyle(text, colorChar, TextFormatting.WHITE);
	}

	public static IFormattableTextComponent convertCharStyleToComponentStyle(String text, char colorChar, TextFormatting defaultColor) {
		String regex = "(?i)" + CharacterHelper.getJavaUnicodeCode(colorChar) + "[0-9A-FK-OR]|" + CharacterHelper.getJavaUnicodeCode(colorChar) + "#[0-9A-F]{6}";
		String[] textParts = text.split(regex);

		List<String> styles = new ArrayList<>();
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(text);
		while(m.find()) 
			styles.add(m.group());

		StringTextComponent root = null;
		List<Object> formattings = null;
		for (int i = 0; i < textParts.length; i++) {
			String tp = textParts[i];

			if(formattings == null) formattings = new ArrayList<>();
			else {
				String controlString = styles.get(i - 1);
				char formatCode = controlString.charAt(1);
				if(formatCode == '#') {
					Color c = Color.fromHex(controlString.substring(1));
					if(c != null) formattings.add(c);
				} else {
					TextFormatting format = getFormattingByFormattingChar(formatCode);
					if(format != null) formattings.add(format);
				}
			}

			if(tp.isEmpty()) continue;

			if(root == null) {
				root = new StringTextComponent(tp);
				for(Object o : formattings) {
					if(o instanceof TextFormatting) {
						TextFormatting formatting = (TextFormatting) o;
						root.mergeStyle(formatting);
						if(formatting == TextFormatting.RESET) 
							root.setStyle(TextComponentHelper.resetStyleNoColor.setColor(Color.fromTextFormatting(defaultColor)));
					} else if(o instanceof Color) {
						TextComponentHelper.setStyleColor(root, (Color) o);
					}
				}
				formattings.clear();
			} else {
				StringTextComponent stc = new StringTextComponent(tp);
				for(Object o : formattings) {
					if(o instanceof TextFormatting) {
						TextFormatting formatting = (TextFormatting) o;
						stc.mergeStyle(formatting);
						if(formatting == TextFormatting.RESET) 
							stc.setStyle(TextComponentHelper.resetStyleNoColor.setColor(Color.fromTextFormatting(defaultColor)));
					} else if(o instanceof Color) {
						TextComponentHelper.setStyleColor(stc, (Color) o);
					}
				}
				formattings.clear();
				root.appendSibling(stc);
			}
		}

		return root;
	}

	public static TextFormatting getFormattingByFormattingChar(char c) {
		for(TextFormatting textformatting : TextFormatting.values()) {
			if (getFormattingChar(textformatting) == c) {
				return textformatting;
			}
		}
		return null;
	}

	public static char getFormattingChar(TextFormatting format) {
		return format.formattingCode;
	}

}

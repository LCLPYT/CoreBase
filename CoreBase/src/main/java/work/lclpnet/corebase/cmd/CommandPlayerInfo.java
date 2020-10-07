package work.lclpnet.corebase.cmd;

import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.util.Map;

import javax.annotation.Nullable;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.EntitySelector;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import work.lclpnet.corebase.CoreBase;
import work.lclpnet.corebase.util.MessageType;
import work.lclpnet.corebase.util.ObjectHelper;
import work.lclpnet.corebase.util.TextComponentHelper;

public class CommandPlayerInfo extends CommandBase{

	public CommandPlayerInfo() {
		super("playerinfo");
	}

	@Override
	protected LiteralArgumentBuilder<CommandSource> transform(LiteralArgumentBuilder<CommandSource> builder) {
		return builder
				.requires(CoreCommands::permLevel2)
				.executes(this::infoSelf)
				.then(Commands.argument("target", EntityArgument.player())
						.executes(this::infoOther));
	}

	public int infoSelf(CommandContext<CommandSource> ctx) throws CommandSyntaxException {
		if(!CoreCommands.isPlayer(ctx.getSource())) {
			ctx.getSource().sendErrorMessage(CoreBase.TEXT.message("Non-players have to specify a target! e.g. /playerinfo <name>", MessageType.ERROR));
			return 1;
		}

		info(ctx.getSource(), ctx.getSource().asPlayer());

		return 0;
	}

	public int infoOther(CommandContext<CommandSource> ctx) throws CommandSyntaxException {
		EntitySelector selector = ctx.getArgument("target", EntitySelector.class);
		ServerPlayerEntity target = selector.selectOnePlayer(ctx.getSource());
		if(target == null) return 1;

		info(ctx.getSource(), target);

		return 0;
	}

	private void info(CommandSource source, ServerPlayerEntity target) {
		try {
			msg(source, "GENERAL", null);
			msg(source, "NAME", target.getName().getString());
			msg(source, "UUID", target.getUniqueID().toString());

			if(target.connection.getNetworkManager().isLocalChannel()) msg(source, "IP-ADDRESS", target.connection.getNetworkManager().getRemoteAddress().toString());
			else msg(source, "IP-ADDRESS", ((InetSocketAddress) target.connection.getNetworkManager().getRemoteAddress()).getHostString());

			msg(source, "LANGUAGE", (String) ObjectHelper.get(target, "language"));

			msg(source, "STATUS", null);
			msg(source, "GAME-MODE", target.interactionManager.getGameType().name());
			msg(source, "HEALTH", String.format("%.2f", target.getHealth()));
			msg(source, "FOOD-LEVEL", target.getFoodStats().getFoodLevel());
			msg(source, "SATURATION-LEVEL", String.format("%.2f", target.getFoodStats().getSaturationLevel()));
			msg(source, "ALLOW-FLIGHT", target.abilities.allowFlying);
			msg(source, "EXPERIENCE", target.experience);
			msg(source, "EXP-LEVEL", target.experienceLevel);
			msg(source, "TOTAL-EXP", target.experienceTotal);
			msg(source, "WALK-SPEED", target.abilities.getWalkSpeed());
			msg(source, "FLY-SPEED", target.abilities.getFlySpeed());
			msg(source, "DIMENSION", target.world.func_234923_W_().func_240901_a_());
			msg(source, "INVULNERABLE", target.abilities.disableDamage);

			msg(source, "ATTRIBUTES", null); // TODO get from attributes modifier map of entity
			try {
				Field f = ObfuscationReflectionHelper.findField(AttributeModifierManager.class, "field_233775_b_");
				f.setAccessible(true);
				@SuppressWarnings("unchecked")
				Map<Attribute, ModifiableAttributeInstance> map = (Map<Attribute, ModifiableAttributeInstance>) f.get(target.func_233645_dx_());
				System.out.println(map);
				map.forEach((attr, instance) -> msg(source, new TranslationTextComponent(attr.func_233754_c_()), instance.getValue()));
			} catch (ReflectiveOperationException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void msg(CommandSource source, IFormattableTextComponent itc, @Nullable Object value) {
		if(value == null) source.sendFeedback(TextComponentHelper.applyTextStyle(itc, TextFormatting.BLUE), false);
		else {
			ITextComponent sibling = TextComponentHelper.applyTextStyle(new StringTextComponent(value.toString()), TextFormatting.GREEN);
			ITextComponent feedback = TextComponentHelper.appendSibling(
					TextComponentHelper.appendText(
							TextComponentHelper.applyTextStyle(
									itc,
									TextFormatting.DARK_PURPLE),
							": "),
					sibling);

			source.sendFeedback(feedback, false);
		}
	}

	private void msg(CommandSource source, String category, @Nullable Object value) {
		msg(source, new StringTextComponent(category), value);
	}

}

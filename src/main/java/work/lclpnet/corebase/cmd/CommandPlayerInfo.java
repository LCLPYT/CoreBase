package work.lclpnet.corebase.cmd;

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
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import work.lclpnet.corebase.CoreBase;
import work.lclpnet.corebase.util.MessageType;
import work.lclpnet.corebase.util.ObjectHelper;

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
			msg(source, "DIMENSION", target.world.getDimensionKey().getLocation());
			msg(source, "INVULNERABLE", target.abilities.disableDamage);

			msg(source, "ATTRIBUTES", null);
			Map<Attribute, ModifiableAttributeInstance> map = target.getAttributeManager().instanceMap;
			map.forEach((attr, instance) -> msg(source, new TranslationTextComponent(attr.getAttributeName()), instance.getValue()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void msg(CommandSource source, IFormattableTextComponent itc, @Nullable Object value) {
		if(value == null) source.sendFeedback(itc.mergeStyle(TextFormatting.BLUE), false);
		else {
			ITextComponent sibling = new StringTextComponent(value.toString()).mergeStyle(TextFormatting.GREEN);
			ITextComponent feedback = itc.mergeStyle(TextFormatting.DARK_PURPLE).appendString(": ").appendSibling(sibling);

			source.sendFeedback(feedback, false);
		}
	}

	private void msg(CommandSource source, String category, @Nullable Object value) {
		msg(source, new StringTextComponent(category), value);
	}

}

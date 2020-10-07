package work.lclpnet.corebase.cmd;

import java.net.InetSocketAddress;

import javax.annotation.Nullable;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.EntitySelector;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
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
		msg(source, "GENRAL", null);
		msg(source, "NAME", target.getName().getString());
		msg(source, "UUID", target.getUniqueID().toString());
		msg(source, "IP-ADRESS", ((InetSocketAddress) target.connection.getNetworkManager().getRemoteAddress()).getHostString());
		msg(source, "LANGUAGE", (String) ObjectHelper.get(target, "language"));

		msg(source, "STATUS", null);
		msg(source, "GAMEMODE", target.interactionManager.getGameType().name());
		msg(source, "HEALTH", String.format("%.2f", target.getHealth()));
		msg(source, "FOOD-LEVEL", target.getFoodStats().getFoodLevel());
		msg(source, "SATURAION-LEVEL", String.format("%.2f", target.getFoodStats().getSaturationLevel()));
		msg(source, "ALLOW-FLIGHT", target.abilities.allowFlying);
		msg(source, "EXPERIENCE", target.experience);
		msg(source, "EXP-LEVEL", target.experienceLevel);
		msg(source, "TOTAL-EXP", target.experienceTotal);
		msg(source, "WALKSPEED", target.abilities.getWalkSpeed());
		msg(source, "FLYSPEED", target.abilities.getFlySpeed());
		msg(source, "DIMENSION", target.world.func_234923_W_());
		msg(source, "INVULNERABLE", target.abilities.disableDamage);

		msg(source, "ATTRIBUTES", null); // TODO get from attributes modifier map of entity
		attr(source, "ATTACK-SPEED", target, Attributes.field_233825_h_);
		attr(source, "ARMOR", target, Attributes.field_233826_i_);
		attr(source, "ARMOR-TOUGHNESS", target, Attributes.field_233827_j_);
		attr(source, "ATTACK-DAMAGE", target, Attributes.field_233823_f_);
		attr(source, "KNOCKBACK-RESISTANCE", target, Attributes.field_233820_c_);
		attr(source, "LUCK", target, Attributes.field_233828_k_);
		attr(source, "MAX-HEALTH", target, Attributes.field_233818_a_);
		attr(source, "MOVEMENT-SPEED", target, Attributes.field_233821_d_);
		attr(source, "FLYING-SPEED", target, Attributes.field_233822_e_);
	}

	private void attr(CommandSource source, String category, ServerPlayerEntity target, Attribute attr) {
		ModifiableAttributeInstance attribute = target.getAttribute(attr);
		if(attribute != null) msg(source, category, attribute.getValue());
	}

	private void msg(CommandSource source, String category, @Nullable Object value) {
		if(value == null) source.sendFeedback(TextComponentHelper.applyTextStyle(new StringTextComponent(category), TextFormatting.BLUE), false);
		else {
			ITextComponent sibling = TextComponentHelper.applyTextStyle(new StringTextComponent(value.toString()), TextFormatting.GREEN);
			ITextComponent feedback = TextComponentHelper.appendSibling(
					TextComponentHelper.appendText(
							TextComponentHelper.applyTextStyle(
									new StringTextComponent(category), 
									TextFormatting.DARK_PURPLE),
							": "), 
					sibling);
			
			source.sendFeedback(feedback, false);
		}
	}

}

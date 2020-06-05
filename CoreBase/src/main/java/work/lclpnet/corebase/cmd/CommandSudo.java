package work.lclpnet.corebase.cmd;

import java.util.List;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.EntitySelector;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.client.CChatMessagePacket;
import net.minecraft.util.text.TextFormatting;
import work.lclpnet.corebase.CoreBase;
import work.lclpnet.corebase.util.MessageType;
import work.lclpnet.corebase.util.Substitute;

public class CommandSudo extends CommandBase{

	public CommandSudo() {
		super("sudo");
	}

	@Override
	protected LiteralArgumentBuilder<CommandSource> transform(LiteralArgumentBuilder<CommandSource> builder) {
		return builder
				.requires(CoreCommands::permLevel2)
				.then(Commands.argument("target", EntityArgument.players())
						.then(Commands.argument("chat", StringArgumentType.greedyString())
								.executes(this::sudo)));
	}
	
	public int sudo(CommandContext<CommandSource> ctx) throws CommandSyntaxException {
		EntitySelector sel = ctx.getArgument("target", EntitySelector.class);
		List<ServerPlayerEntity> players = sel.selectPlayers(ctx.getSource());
		
		if(players.isEmpty()) {
			ctx.getSource().sendErrorMessage(CoreBase.TEXT.message("No player was found.", MessageType.ERROR));
			return 1;
		}
		
		String chat = ctx.getArgument("chat", String.class);
		
		players.forEach(p -> p.connection.processChatMessage(new CChatMessagePacket(chat)));
		ctx.getSource().sendFeedback(CoreBase.TEXT.complexMessage("Successfully executed as %s.", TextFormatting.GREEN, new Substitute(EntitySelector.joinNames(players).getString(), TextFormatting.YELLOW)), false);
		return 0;
	}

}

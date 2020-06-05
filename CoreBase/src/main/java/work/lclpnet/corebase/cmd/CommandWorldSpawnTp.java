package work.lclpnet.corebase.cmd;

import java.util.List;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.EntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.server.ServerWorld;
import work.lclpnet.corebase.CoreBase;
import work.lclpnet.corebase.util.EntityHelper;
import work.lclpnet.corebase.util.MessageType;
import work.lclpnet.corebase.util.Substitute;

public class CommandWorldSpawnTp extends CommandBase{

	public CommandWorldSpawnTp() {
		super("worldspawntp");
	}
	
	@Override
	protected LiteralArgumentBuilder<CommandSource> transform(LiteralArgumentBuilder<CommandSource> builder) {
		return builder
				.requires(CoreCommands::permLevel2)
				.executes(this::tpSelf)
				.then(Commands.argument("target", EntityArgument.players())
						.executes(this::tpOther));
	}
	
	public int tpSelf(CommandContext<CommandSource> ctx) {
		if(!CoreCommands.isEntity(ctx.getSource())) {
			ctx.getSource().sendErrorMessage(CoreBase.TEXT.message("Non-players have to specify a target. e.g. /worldspawntp <player name>", MessageType.ERROR));
			return 1;
		}
		
		tp(ctx.getSource().getEntity());
		ctx.getSource().sendFeedback(CoreBase.TEXT.message("You teleported to the world spawn.", MessageType.SUCCESS), false);
		return 0;
	}
	
	public int tpOther(CommandContext<CommandSource> ctx) throws CommandSyntaxException {
		EntitySelector sel = ctx.getArgument("target", EntitySelector.class);
		List<? extends Entity> entities = sel.select(ctx.getSource());
		if(entities.isEmpty()) {
			ctx.getSource().sendErrorMessage(CoreBase.TEXT.message("No entities were found.", MessageType.ERROR));
			return 1;
		}
		
		entities.forEach(this::tp);
		ctx.getSource().sendFeedback(CoreBase.TEXT.complexMessage("Teleported %s to the world spawn.", TextFormatting.GREEN, new Substitute(EntitySelector.joinNames(entities).getString(), TextFormatting.YELLOW)), false);
		return 0;
	}
	
	private void tp(Entity entity) {
		if(!(entity.world instanceof ServerWorld)) return;
		
		BlockPos spawn = entity.world.getSpawnPoint();
		EntityHelper.teleport(entity, (ServerWorld) entity.world, spawn.getX() + 0.5D, (double) spawn.getY(), spawn.getZ() + 0.5D, entity.rotationYaw, entity.rotationPitch);
	}


}

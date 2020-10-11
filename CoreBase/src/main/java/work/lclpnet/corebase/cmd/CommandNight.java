package work.lclpnet.corebase.cmd;

import javax.annotation.Nullable;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.DimensionArgument;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.server.ServerWorld;
import work.lclpnet.corebase.CoreBase;
import work.lclpnet.corebase.util.MessageType;
import work.lclpnet.corebase.util.Substitute;

public class CommandNight extends CommandBase{

	public CommandNight() {
		super("night");
	}

	@Override
	protected LiteralArgumentBuilder<CommandSource> transform(LiteralArgumentBuilder<CommandSource> builder) {
		return builder
				.requires(CoreCommands::permLevel2)
				.executes(CommandNight::night)
				.then(Commands.argument("world", DimensionArgument.getDimension())
						.executes(CommandNight::nightWorld));
	}
	
	private static int night(CommandContext<CommandSource> ctx) {
		if(!CoreCommands.isEntity(ctx.getSource())) {
			ctx.getSource().sendErrorMessage(CoreBase.TEXT.message("Non-Entities have to specify a worldname! e.g. /night minecraft:overworld", MessageType.ERROR));
			return 1;
		}
		
		Entity en = ctx.getSource().getEntity();

		setTime((ServerWorld) en.world, en);
		
		return 0;
	}

	private static int nightWorld(CommandContext<CommandSource> ctx) {
		ServerWorld dim;
		try {
			dim = DimensionArgument.getDimensionArgument(ctx, "world");
		} catch (CommandSyntaxException e) {
			e.printStackTrace();
			dim = null;
		}
		if(dim == null) return 1;
		
		setTime(dim, null);
		
		ResourceLocation dimId = dim.getDimensionKey().getLocation();
		ctx.getSource().sendFeedback(CoreBase.TEXT.message("Set the time in world " + dimId + " to night.", MessageType.SUCCESS), false);
		return 0;
	}
	
	private static void setTime(ServerWorld world, @Nullable Entity en) {
		world.func_241114_a_(16000L);
		
		final ITextComponent msg = CoreBase.TEXT.complexMessage("%s has set the time to night.", TextFormatting.GREEN, 
				new Substitute(en != null ? en.getDisplayName().getString() : "Console", TextFormatting.YELLOW));
		
		world.getPlayers().forEach(p -> {
			p.playSound(SoundEvents.BLOCK_ANVIL_LAND, SoundCategory.BLOCKS, 1F, 0F);
			p.sendMessage(msg, Util.DUMMY_UUID);
		});
	}

}

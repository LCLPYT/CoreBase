package work.lclpnet.corebase.cmd;

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

import javax.annotation.Nullable;

public class CommandDay extends CommandBase {

    public CommandDay() {
        super("day");
    }

    @Override
    protected LiteralArgumentBuilder<CommandSource> transform(LiteralArgumentBuilder<CommandSource> builder) {
        return builder
                .requires(CoreCommands::permLevel2)
                .executes(CommandDay::day)
                .then(Commands.argument("world", DimensionArgument.getDimension())
                        .executes(CommandDay::dayWorld));
    }

    private static int day(CommandContext<CommandSource> ctx) {
        if (!CoreCommands.isEntity(ctx.getSource())) {
            ctx.getSource().sendErrorMessage(CoreBase.TEXT.message("Non-Entities have to specify a worldname! e.g. /day minecraft:overworld", MessageType.ERROR));
            return 1;
        }

        Entity en = ctx.getSource().getEntity();

        setTime((ServerWorld) en.world, en);

        return 0;
    }

    private static int dayWorld(CommandContext<CommandSource> ctx) {
        ServerWorld dim;
        try {
            dim = DimensionArgument.getDimensionArgument(ctx, "world");
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
            dim = null;
        }
        if (dim == null) return 1;

        setTime(dim, ctx.getSource().getEntity());

        ResourceLocation dimId = dim.getDimensionKey().getLocation();
        ctx.getSource().sendFeedback(CoreBase.TEXT.message("Set the time in world " + dimId + " to day.", MessageType.SUCCESS), false);
        return 0;
    }

    private static void setTime(ServerWorld world, @Nullable Entity en) {
        world.setDayTime(6000L);

        final ITextComponent msg = CoreBase.TEXT.complexMessage("%s has set the time to day.", TextFormatting.GREEN,
                new Substitute(en != null ? en.getDisplayName().getString() : "Console", TextFormatting.YELLOW));

        world.getPlayers().forEach(p -> {
            p.playSound(SoundEvents.BLOCK_ANVIL_LAND, SoundCategory.BLOCKS, 1F, 0F);
            p.sendMessage(msg, Util.DUMMY_UUID);
        });
    }

}

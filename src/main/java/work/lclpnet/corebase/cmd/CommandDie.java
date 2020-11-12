package work.lclpnet.corebase.cmd;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.EntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.text.TextFormatting;
import work.lclpnet.corebase.CoreBase;
import work.lclpnet.corebase.util.MessageType;
import work.lclpnet.corebase.util.Substitute;

public class CommandDie extends CommandBase{

	public CommandDie() {
		super("die");
	}
	
	@Override
	protected LiteralArgumentBuilder<CommandSource> transform(LiteralArgumentBuilder<CommandSource> builder) {
		return builder
				.requires(CoreCommands::permLevel2)
				.executes(this::dieSelf)
				.then(Commands.argument("target", EntityArgument.entities())
						.executes(this::dieMany));
	}
	
	public int dieSelf(CommandContext<CommandSource> ctx) {
		if(!CoreCommands.isEntity(ctx.getSource())) {
			ctx.getSource().sendErrorMessage(CoreBase.TEXT.message("Non-entities have to supply a target. e.g. /die <name>", MessageType.ERROR));
			return 1;
		}
		
		die(ctx.getSource().getEntity(), null);
		
		ctx.getSource().sendFeedback(CoreBase.TEXT.message("You killed yourself.", MessageType.SUCCESS), false);
		
		return 0;
	}
	
	public int dieMany(CommandContext<CommandSource> ctx) throws CommandSyntaxException {
		EntitySelector selector = ctx.getArgument("target", EntitySelector.class);
		List<? extends Entity> targets = selector.select(ctx.getSource());
		
		if(targets == null || targets.isEmpty()) {
			ctx.getSource().sendErrorMessage(CoreBase.TEXT.message("No players were found.", MessageType.ERROR));
			return 1;
		}

		List<Entity> died = new ArrayList<>();
		targets.forEach(en -> die(en, died::add));
		
		if(died.isEmpty()) ctx.getSource().sendErrorMessage(CoreBase.TEXT.message("None of the entites you supplied are alive (have health) and therefore can't be killed.", MessageType.ERROR));
		else ctx.getSource().sendFeedback(CoreBase.TEXT.complexMessage("You killed %s.", TextFormatting.GREEN, new Substitute(EntitySelector.joinNames(died).getString(), TextFormatting.YELLOW)), false);
		
		return 0;
	}

	public void die(Entity en, @Nullable Consumer<Entity> onKilled) {
		if(!(en instanceof LivingEntity)) return;
		((LivingEntity) en).setHealth(0F);
		if(onKilled != null) onKilled.accept(en);
	}
	
}

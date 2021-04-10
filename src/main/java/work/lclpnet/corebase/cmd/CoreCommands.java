package work.lclpnet.corebase.cmd;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands.EnvironmentType;
import net.minecraft.entity.player.PlayerEntity;

import java.util.function.Predicate;

public class CoreCommands {

    public static void registerCommands(CommandDispatcher<CommandSource> dispatcher, EnvironmentType environmentType) {
        new CommandChatClear().register(dispatcher);
        new CommandChest().register(dispatcher);
        new CommandInventory().register(dispatcher);
        new CommandCraft().register(dispatcher);
        new CommandDay().register(dispatcher);
        new CommandNight().register(dispatcher);
        new CommandDie().register(dispatcher);
        new CommandFeed().register(dispatcher);
        new CommandHeal().register(dispatcher);
        new CommandPlayerInfo().register(dispatcher);
        new CommandRawSay().register(dispatcher);
        new CommandRename().register(dispatcher);
        new CommandSpeed().register(dispatcher);
        new CommandWorldSpawnTp().register(dispatcher);
        new CommandFly().register(dispatcher);

        if (environmentType != EnvironmentType.INTEGRATED) {
            new CommandCrash().register(dispatcher);
            new CommandPing().register(dispatcher);
            new CommandSudo().register(dispatcher);
        }
    }

    public static boolean permLevel1(CommandSource cs) {
        return cs.hasPermissionLevel(1);
    }

    public static boolean permLevel2(CommandSource cs) {
        return cs.hasPermissionLevel(2);
    }

    public static boolean permLevel3(CommandSource cs) {
        return cs.hasPermissionLevel(3);
    }

    public static boolean permLevel4(CommandSource cs) {
        return cs.hasPermissionLevel(4);
    }

    public static boolean isPlayer(CommandSource cs) {
        return cs.getEntity() != null && cs.getEntity() instanceof PlayerEntity;
    }

    public static boolean isEntity(CommandSource cs) {
        return cs.getEntity() != null;
    }

    public static boolean isPlayerPermLevel2(CommandSource cs) {
        return permLevel2(cs) && isPlayer(cs);
    }

    @SafeVarargs
    public static boolean requires(CommandSource cs, Predicate<CommandSource>... predicates) {
        for (Predicate<CommandSource> p : predicates)
            if (!p.test(cs)) return false;
        return true;
    }

}

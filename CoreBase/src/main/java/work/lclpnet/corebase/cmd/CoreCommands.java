package work.lclpnet.corebase.cmd;

import java.util.function.Predicate;

import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.PlayerEntity;

public class CoreCommands {

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
		for(Predicate<CommandSource> p : predicates) 
			if(!p.test(cs)) return false;
		return true;
	}
	
}

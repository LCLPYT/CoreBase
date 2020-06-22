package work.lclpnet.corebase.util;

import java.lang.reflect.Field;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class ServerHelper {

	public static boolean isBonusChestEnabled(MinecraftServer server) {
		try {
			Field f = ObfuscationReflectionHelper.findField(MinecraftServer.class, "field_71289_N");
			return (boolean) f.get(server);
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
			return false;
		}
	}
	
}

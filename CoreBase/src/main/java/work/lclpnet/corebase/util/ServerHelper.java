package work.lclpnet.corebase.util;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.gen.settings.DimensionGeneratorSettings;
import net.minecraft.world.storage.IServerConfiguration;

public class ServerHelper {

	public static boolean isBonusChestEnabled(MinecraftServer server) {
		IServerConfiguration config = server.func_240793_aU_();
		DimensionGeneratorSettings dgs = config.getDimensionGeneratorSettings();
		return dgs.hasBonusChest();
	}
	
}

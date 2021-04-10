package work.lclpnet.corebase.util;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.gen.settings.DimensionGeneratorSettings;
import net.minecraft.world.storage.IServerConfiguration;

public class ServerHelper {

    public static boolean isBonusChestEnabled(MinecraftServer server) {
        IServerConfiguration config = server.getServerConfiguration();
        DimensionGeneratorSettings dgs = config.getDimensionGeneratorSettings();
        return dgs.hasBonusChest();
    }

}

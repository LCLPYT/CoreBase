package work.lclpnet.corebase;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppedEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import work.lclpnet.corebase.cmd.CoreCommands;
import work.lclpnet.corebase.util.ComponentSupplier;

@Mod(CoreBase.MODID)
public class CoreBase {

    public static final String MODID = "corebase";
    private static final Logger LOGGER = LogManager.getLogger();
    public static final ComponentSupplier TEXT = new ComponentSupplier("CoreBase");
    public static volatile boolean active = false;
    private static MinecraftServer server = null;

    public CoreBase() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        IEventBus bus = MinecraftForge.EVENT_BUS;
        bus.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) { //preinit
        LOGGER.info("CoreBase initializing...");

        LOGGER.info("CoreBase initialized.");
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent e) {
        LOGGER.info("CoreBase starting...");
        server = e.getServer();
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent e) {
        CoreCommands.registerCommands(e.getDispatcher(), e.getEnvironment());
    }

    @SubscribeEvent
    public void onServerStarted(FMLServerStartedEvent e) {
        LOGGER.info("CoreBase started.");
        active = true;
    }

    @SubscribeEvent
    public void onServerStop(FMLServerStoppingEvent e) {
        LOGGER.info("CoreBase stopping...");
        active = false;
    }

    @SubscribeEvent
    public void onServerStopped(FMLServerStoppedEvent e) {
        LOGGER.info("CoreBase stopped.");
    }

    public static MinecraftServer getServer() {
        return server;
    }

}

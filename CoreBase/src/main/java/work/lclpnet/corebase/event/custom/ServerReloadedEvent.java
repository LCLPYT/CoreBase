package work.lclpnet.corebase.event.custom;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.eventbus.api.Event;

/**
 * Called when the server finsished reloading. For example, after the /reload command is issued
 */
public class ServerReloadedEvent extends Event{
	
	private final MinecraftServer server;
	
	public ServerReloadedEvent(MinecraftServer server) {
		this.server = server;
	}
	
	public MinecraftServer getServer() {
		return server;
	}
	
}

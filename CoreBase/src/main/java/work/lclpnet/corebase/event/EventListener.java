package work.lclpnet.corebase.event;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import work.lclpnet.corebase.CoreBase;
import work.lclpnet.corebase.event.custom.PlayerQuitEvent;
import work.lclpnet.corebase.util.MessageType;

@EventBusSubscriber(modid = CoreBase.MODID, bus = Bus.FORGE)
public class EventListener {

	@SubscribeEvent
	public static void onQuitEvent(PlayerQuitEvent e) {
		System.out.println("Quit.!");
		e.setQuitMessage(CoreBase.TEXT.message("QUIT> " + e.getPlayer().getName().getString(), MessageType.INFO));
	}
	
}

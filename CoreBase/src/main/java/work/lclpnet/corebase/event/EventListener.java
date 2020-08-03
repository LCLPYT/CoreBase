package work.lclpnet.corebase.event;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import work.lclpnet.corebase.CoreBase;
import work.lclpnet.corebase.event.custom.BlockStateToStateEvent;

@EventBusSubscriber(modid = CoreBase.MODID, bus = Bus.FORGE)
public class EventListener {

	@SubscribeEvent
	public static void onIceMelt(BlockStateToStateEvent e) {
		System.out.println("MELT");
		e.setCanceled(true);
	}
	
}

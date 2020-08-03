package work.lclpnet.corebase.event;

import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import work.lclpnet.corebase.CoreBase;

@EventBusSubscriber(modid = CoreBase.MODID, bus = Bus.FORGE)
public class EventListener {
	
	/*@SubscribeEvent
	public static void onSnowMelt(BlockStateToStateEvent e) {
		e.setCanceled(true);
		System.out.println("STS: " + e.getState() + " -> " + e.getTo());
	}*/
	
}

package work.lclpnet.corebase.event;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.world.BlockEvent.EntityPlaceEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import work.lclpnet.corebase.CoreBase;

@EventBusSubscriber(modid = CoreBase.MODID, bus = Bus.FORGE)
public class EventListener {

	// Prevents inventory de-sync
	@SubscribeEvent(priority = EventPriority.LOWEST, receiveCanceled = true)
	public static void onPlaceLast(EntityPlaceEvent e) {
		if(e.isCanceled() && e.getEntity() instanceof ServerPlayerEntity) {
			((ServerPlayerEntity) e.getEntity()).sendContainerToPlayer(((ServerPlayerEntity) e.getEntity()).openContainer); //remove this when MC-99075 fixed
		}
	}

}

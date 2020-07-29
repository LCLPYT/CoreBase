package work.lclpnet.corebase.event;

import net.minecraft.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.Cancelable;

/**
 * Called, when a player tries to freeze blocks with frostwalker enchanted boots.
 * This event is {@link Cancelable}.
 */
@Cancelable
public class FrostWalkerEvent extends LivingEvent{

	public FrostWalkerEvent(LivingEntity issuer) {
		super(issuer);
	}
	
}

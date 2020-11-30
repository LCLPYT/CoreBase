package work.lclpnet.corebase.event.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Cancelable;

/**
 * Called when a something happens with the player's food.
 * Some children are {@link Cancelable}. Please check {@link #isCancelable()} first.
 */
public class FoodEvent extends PlayerEvent {

	public FoodEvent(PlayerEntity player) {
		super(player);
	}

}

package work.lclpnet.corebase.event.custom;

import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Cancelable;

/**
 * Called when a player tries to manipulate an armor stand.
 * This event is This event is {@link Cancelable}
 */
@Cancelable
public class PlayerArmorStandManipulateEvent extends PlayerEvent {

    private ArmorStandEntity target;

    public PlayerArmorStandManipulateEvent(final PlayerEntity player, ArmorStandEntity target) {
        super(player);
        this.target = target;
    }

    public ArmorStandEntity getTarget() {
		return target;
	}

}

package work.lclpnet.corebase.event.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.eventbus.api.Cancelable;

/**
 * Called when a player's food level changes.
 * This event is {@link Cancelable}
 */
@Cancelable
public class FoodLevelChangeEvent extends FoodEvent {

    private int fromLevel, toLevel;

    public FoodLevelChangeEvent(PlayerEntity player, int fromLevel, int toLevel) {
        super(player);
        this.fromLevel = fromLevel;
        this.toLevel = toLevel;
    }

    public int getFromLevel() {
        return fromLevel;
    }

    public int getToLevel() {
        return toLevel;
    }

    public void setToLevel(int toLevel) {
        this.toLevel = toLevel;
    }

}

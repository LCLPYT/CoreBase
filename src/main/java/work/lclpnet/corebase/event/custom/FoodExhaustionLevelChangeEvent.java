package work.lclpnet.corebase.event.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.eventbus.api.Cancelable;

/**
 * Called when a player's food exhaustion level changes.
 * This event is {@link Cancelable}
 */
@Cancelable
public class FoodExhaustionLevelChangeEvent extends FoodEvent {

	private float fromLevel, toLevel;
	
	public FoodExhaustionLevelChangeEvent(PlayerEntity player, float exhaustionLevelFrom, float exhaustionLevelTo) {
		super(player);
		this.fromLevel = exhaustionLevelFrom;
		this.toLevel = exhaustionLevelTo;
	}
	
	public float getFromLevel() {
		return fromLevel;
	}
	
	public float getToLevel() {
		return toLevel;
	}
	
	public void setToLevel(float toLevel) {
		this.toLevel = toLevel;
	}

}

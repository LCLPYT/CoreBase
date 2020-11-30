package work.lclpnet.corebase.event.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.eventbus.api.Cancelable;

/**
 * Called when a player's food saturation level changes.
 * This event is {@link Cancelable}
 */
@Cancelable
public class FoodSaturationLevelChangeEvent extends FoodEvent {

	private float fromLevel, toLevel;
	
	public FoodSaturationLevelChangeEvent(PlayerEntity player, float saturationLevelFrom, float saturationLevelTo) {
		super(player);
		this.fromLevel = saturationLevelFrom;
		this.toLevel = saturationLevelTo;
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

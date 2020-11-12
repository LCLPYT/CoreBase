package work.lclpnet.corebase.event.custom;

import javax.annotation.Nullable;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Cancelable;

/**
 * Called when a players food level changes.
 * This event is This event is {@link Cancelable}
 */
@Cancelable
@Deprecated
public class FoodLevelChangeEvent extends PlayerEvent{

	private int fromLevel, toLevel;
	private ItemStack item;
	private Food food;
	
	public FoodLevelChangeEvent(PlayerEntity player, int fromLevel, int toLevel) {
		this(player, fromLevel, toLevel, null, null);
	}
	
	public FoodLevelChangeEvent(PlayerEntity player, int fromLevel, int toLevel, @Nullable ItemStack item, @Nullable Food food) {
		super(player);
		this.fromLevel = fromLevel;
		this.toLevel = toLevel;
		this.item = item;
		this.food = food;
	}
	
	public ItemStack getItem() {
		return item;
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
	
	public Food getFood() {
		return food;
	}

}

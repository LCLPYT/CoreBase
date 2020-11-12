package work.lclpnet.corebase.asm.type;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.ItemStack;

public interface FoodChangeable {

	/**
	 * Called when the food level changes.
	 * 
	 * @return true, if the food change should be cancelled.
	 */
	boolean onChangeFoodLevel(PlayerEntity player, int toLevel, Food food, ItemStack item);
	
}

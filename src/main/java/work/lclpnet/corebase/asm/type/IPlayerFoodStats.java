package work.lclpnet.corebase.asm.type;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.FoodStats;

public interface IPlayerFoodStats {

	void setPlayer(PlayerEntity player);
	
	PlayerEntity getPlayer();
	
	public static IPlayerFoodStats get(FoodStats stats) {
		return (IPlayerFoodStats) stats;
	}
	
}

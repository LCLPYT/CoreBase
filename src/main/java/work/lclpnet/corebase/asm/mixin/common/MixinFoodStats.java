package work.lclpnet.corebase.asm.mixin.common;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.item.Food;
import net.minecraft.item.ItemStack;
import net.minecraft.util.FoodStats;

@Mixin(value = FoodStats.class, remap = false)
public abstract class MixinFoodStats {

	/*@Override
	public boolean onChangeFoodLevel(PlayerEntity player, int toLevel, Food food, ItemStack item) {
		FoodLevelChangeEvent e = new FoodLevelChangeEvent(player, player.getFoodStats().getFoodLevel(), toLevel, item, food);
		MinecraftForge.EVENT_BUS.post(e);
		return e.isCanceled();
	}*/
	
	@Inject(
			method = "Lnet/minecraft/util/FoodStats;addStats(IF)V", 
			at = @At(
					value = "FIELD",
					target = "Lnet/minecraft/util/FoodStats;foodLevel:I",
					opcode = Opcodes.PUTFIELD
					),
			cancellable = true)
	public void onModifyFoodLevel(int foodLevelIn, float foodSaturationModifier, Food food, ItemStack foodItem, CallbackInfo ci) {
		System.out.println("FOOD LEVEL MODIFY");
	}
	
}

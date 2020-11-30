package work.lclpnet.corebase.asm.mixin.common;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.FoodStats;
import work.lclpnet.corebase.asm.type.IPlayerFoodStats;

@Mixin(PlayerEntity.class)
public class MixinPlayerEntity {

	@Shadow
	protected FoodStats foodStats;
	
	@Inject(
			method = "<init>*",
			at = @At("RETURN")
			)
	public void onInit(CallbackInfo ci) {
		IPlayerFoodStats.get(foodStats).setPlayer((PlayerEntity) (Object) this);
	}
	
}

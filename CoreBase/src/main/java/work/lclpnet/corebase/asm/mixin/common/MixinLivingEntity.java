package work.lclpnet.corebase.asm.mixin.common;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity {

	@Shadow
	public abstract float getHealth();
	
	private float mixinHealthBefore = -1F;
	
	@Inject(
			method = "Lnet/minecraft/entity/LivingEntity;attackEntityFrom(Lnet/minecraft/util/DamageSource;F)Z",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/entity/LivingEntity;damageEntity(Lnet/minecraft/util/DamageSource;F)V",
					shift = Shift.BEFORE
					)
			)
	public void beforeDamageEntity(DamageSource source, float damageAmount, CallbackInfoReturnable<Boolean> cir) {
		mixinHealthBefore = getHealth();
	}
	
	@Inject(
			method = "Lnet/minecraft/entity/LivingEntity;attackEntityFrom(Lnet/minecraft/util/DamageSource;F)Z",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/entity/LivingEntity;damageEntity(Lnet/minecraft/util/DamageSource;F)V",
					shift = Shift.AFTER
					),
			cancellable = true
			)
	public void afterDamageEntity(DamageSource source, float damageAmount, CallbackInfoReturnable<Boolean> cir) {
		float before = mixinHealthBefore;
		mixinHealthBefore = -1F;
		if(before != getHealth()) return;
		
		// No damage taken
		cir.setReturnValue(false);
		cir.cancel();
	}
	
}

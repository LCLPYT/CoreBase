package work.lclpnet.corebase.asm.mixin.common;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.enchantment.FrostWalkerEnchantment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import work.lclpnet.corebase.event.custom.FrostWalkerEvent;

@Mixin(FrostWalkerEnchantment.class)
public class MixinFrostWalkerEnchantment {

	@Inject(
			method = "freezeNearby(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;I)V",
					at = @At("HEAD"),
					cancellable = true
			)
	private static void onFreezeNearby(LivingEntity living, World worldIn, BlockPos pos, int level, CallbackInfo ci) {
	       FrostWalkerEvent event = new FrostWalkerEvent(living);
	       MinecraftForge.EVENT_BUS.post(event);
	       if(event.isCanceled()) ci.cancel();
	}
	
}

package work.lclpnet.corebase.asm.mixin.common;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import work.lclpnet.corebase.event.custom.BlockStateToStateEvent;

@Mixin(SnowBlock.class)
public class MixinSnowBlock {

	@Inject(
			method = "randomTick(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/server/ServerWorld;Lnet/minecraft/util/math/BlockPos;Ljava/util/Random;)V",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/block/SnowBlock;spawnDrops(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V"
					),
			cancellable = true
			)
	public void beforeSpawnDrops(BlockState state, ServerWorld world, BlockPos pos, Random ran, CallbackInfo ci) {
		BlockStateToStateEvent event = new BlockStateToStateEvent(world, pos, state, Blocks.AIR.getDefaultState());
		MinecraftForge.EVENT_BUS.post(event);
		if(event.isCanceled()) ci.cancel();
	}

}

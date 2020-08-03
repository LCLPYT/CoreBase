package work.lclpnet.corebase.asm.mixin.common;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IceBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import work.lclpnet.corebase.event.custom.BlockStateToStateEvent;

@Mixin(IceBlock.class)
public class MixinIceBlock {

	@Inject(
			method = "Lnet/minecraft/block/IceBlock;turnIntoWater(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V",
			at = @At("HEAD"),
			cancellable = true
			)
	protected void turnIntoWater(BlockState state, World world, BlockPos pos, CallbackInfo ci) {
	       BlockStateToStateEvent event = new BlockStateToStateEvent(world, pos, state, Blocks.WATER.getDefaultState());
	       MinecraftForge.EVENT_BUS.post(event);
	       if(event.isCanceled()) ci.cancel();
	}
	
}

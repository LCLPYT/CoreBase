package work.lclpnet.corebase.asm.mixin.common;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;
import work.lclpnet.corebase.event.custom.BlockStateToStateEvent;
import work.lclpnet.corebase.event.custom.SnowFallEvent;

@Mixin(ServerWorld.class)
public class MixinServerWorld {

	@Redirect(
			method = "Lnet/minecraft/world/server/ServerWorld;tickEnvironment(Lnet/minecraft/world/chunk/Chunk;I)V",
			at = @At(
					value = "INVOKE",
					target = "setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)Z"
					)
			)
	public boolean onSetBlockState(ServerWorld world, BlockPos pos, BlockState state) {
		Event event = null;
		if(state.getBlock().equals(Blocks.SNOW)) event = new SnowFallEvent(world, pos, state);
		else if(state.getBlock().equals(Blocks.ICE)) event = new BlockStateToStateEvent(world, pos, world.getBlockState(pos), state);
		else return false;
		
		MinecraftForge.EVENT_BUS.post(event);
		
		if(event.isCanceled()) return false;
		return world.setBlockState(pos, state);
	}
	
}

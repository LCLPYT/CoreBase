package work.lclpnet.corebase.asm.mixin.common;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import work.lclpnet.corebase.event.custom.PlayerBucketFillEvent;

@Mixin(BucketItem.class)
public class MixinBucketItem {

	@Shadow
	@Final
	private Fluid containedBlock;

	// Fluid net.minecraft.block.IBucketPickupHandler.pickupFluid(IWorld worldIn, BlockPos pos, BlockState state)
	@Inject(
			method = "Lnet/minecraft/item/BucketItem;onItemRightClick("
					+ "Lnet/minecraft/world/World;"
					+ "Lnet/minecraft/entity/player/PlayerEntity;"
					+ "Lnet/minecraft/util/Hand;"
					+ ")Lnet/minecraft/util/ActionResult;",
					at = @At(
							value = "INVOKE",
							target = "Lnet/minecraft/block/IBucketPickupHandler;pickupFluid("
									+ "Lnet/minecraft/world/IWorld;"
									+ "Lnet/minecraft/util/math/BlockPos;"
									+ "Lnet/minecraft/block/BlockState;"
									+ ")Lnet/minecraft/fluid/Fluid;"
							),
					cancellable = true,
					locals = LocalCapture.CAPTURE_FAILHARD
			)
	public void onItemRightClick(World world, PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult<ItemStack>> cir,
			ItemStack itemstack, RayTraceResult raytraceresult, ActionResult<ItemStack> ret, BlockRayTraceResult blockraytraceresult, 
			BlockPos blockpos, Direction direction, BlockPos blockpos1, BlockState blockstate1) {
		PlayerBucketFillEvent event = new PlayerBucketFillEvent(player, player.world, blockpos, blockpos, direction, itemstack);
		MinecraftForge.EVENT_BUS.post(event);

		if(!event.isCanceled()) return;

		if(player instanceof ServerPlayerEntity) {
			((ServerPlayerEntity) player).sendContainerToPlayer(((ServerPlayerEntity) player).openContainer);
		}

		cir.setReturnValue(ActionResult.resultFail(itemstack));
		cir.cancel();
	}

}

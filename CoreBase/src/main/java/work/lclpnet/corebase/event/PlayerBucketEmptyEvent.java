package work.lclpnet.corebase.event;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraftforge.eventbus.api.Cancelable;

/**
 * Called when a player tries to empty a bucket.
 * This event is {@link Cancelable}.
 */
@Cancelable
public class PlayerBucketEmptyEvent extends PlayerBucketEvent {
	
    public PlayerBucketEmptyEvent(final PlayerEntity player, final IWorld world, final BlockPos block, final BlockPos blockClicked,
    		final Direction blockFace, final ItemStack item) {
        super(player, world, block, blockClicked, blockFace, item);
    }
    
}

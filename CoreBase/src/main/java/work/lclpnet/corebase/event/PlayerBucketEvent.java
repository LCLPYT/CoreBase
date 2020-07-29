package work.lclpnet.corebase.event;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Cancelable;

/**
 * Called when a player makes an interaction involving a bucket.
 * This event is {@link Cancelable}.
 */
@Cancelable
public class PlayerBucketEvent extends PlayerEvent {

	private BlockPos block, blockClicked;
	private Direction blockFace;
	private ItemStack item;
	private IWorld world;
	
    public PlayerBucketEvent(final PlayerEntity player, final IWorld world, final BlockPos block, final BlockPos blockClicked,
    		final Direction blockFace, final ItemStack item) {
        super(player);
        this.world = world;
        this.block = block;
        this.blockClicked = blockClicked;
        this.blockFace = blockFace;
        this.item = item;
    }
    
    public IWorld getWorld() {
		return world;
	}
    
    public BlockPos getBlock() {
		return block;
	}
    
    public BlockPos getBlockClicked() {
		return blockClicked;
	}
    
    public Direction getBlockFace() {
		return blockFace;
	}
    
    public ItemStack getItem() {
		return item;
	}
    
}

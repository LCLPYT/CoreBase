package work.lclpnet.corebase.event;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Cancelable;

/**
 * Called, when a blocks changes it's BlockState. For example, when an ice block turns into water.
 * This event is {@link Cancelable}.
 */
@Cancelable
public class BlockStateToStateEvent extends BlockEvent{

	private BlockState to;
	
	public BlockStateToStateEvent(IWorld world, BlockPos pos, BlockState state, BlockState to) {
		super(world, pos, state);
		this.to = to;
	}
	
	public BlockState getTo() {
		return to;
	}
	
}

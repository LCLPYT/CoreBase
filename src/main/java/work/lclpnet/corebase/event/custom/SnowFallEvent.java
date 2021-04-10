package work.lclpnet.corebase.event.custom;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Cancelable;

/**
 * Called, when snow patches generate due to downfall.
 * This event is {@link Cancelable}.
 */
@Cancelable
public class SnowFallEvent extends BlockEvent {

    public SnowFallEvent(IWorld world, BlockPos pos, BlockState state) {
        super(world, pos, state);
    }

}

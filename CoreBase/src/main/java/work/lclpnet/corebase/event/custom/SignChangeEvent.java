package work.lclpnet.corebase.event.custom;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IWorld;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Cancelable;

/**
 * Called, when a player updates a sign.
 * This event is {@link Cancelable}.
 */
@Cancelable
public class SignChangeEvent extends BlockEvent{

	private ITextComponent[] lines;
	private PlayerEntity player;
	
	public SignChangeEvent(IWorld world, BlockPos pos, BlockState state, ITextComponent[] lines, PlayerEntity player) {
		super(world, pos, state);
		this.lines = lines;
		this.player = player;
	}
	
	public PlayerEntity getPlayer() {
		return player;
	}
	
	public ITextComponent[] getLines() {
		return lines;
	}
	
	public void setLines(ITextComponent[] lines) {
		this.lines = lines;
	}
	
	public void setLine(int lineNumber, ITextComponent s) {
		lines[lineNumber] = s;
	}
	
	public ITextComponent getLine(int lineNumber) {
		return lines[lineNumber];
	}
	
}

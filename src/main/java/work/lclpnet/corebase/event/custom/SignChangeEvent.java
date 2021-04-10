package work.lclpnet.corebase.event.custom;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IWorld;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Cancelable;

import java.util.List;

/**
 * Called, when a player updates a sign.
 * This event is {@link Cancelable}.
 */
@Cancelable
public class SignChangeEvent extends BlockEvent {

    private List<String> lines;
    private ITextComponent[] componentLines;
    private PlayerEntity player;

    public SignChangeEvent(IWorld world, BlockPos pos, BlockState state, List<String> lines, PlayerEntity player) {
        super(world, pos, state);
        this.lines = lines;
        this.componentLines = new ITextComponent[lines.size()];
        this.player = player;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    public List<String> getLines() {
        return lines;
    }

    public void setComponentLine(int lineNumber, ITextComponent s) {
        componentLines[lineNumber] = s;
    }

    public void setLine(int lineNumber, String s) {
        lines.set(lineNumber, s);
    }

    public ITextComponent getComponentLine(int lineNumber) {
        return componentLines[lineNumber];
    }

    public String getLine(int lineNumber) {
        return lines.get(lineNumber);
    }

    public ITextComponent[] getComponentLines() {
        return componentLines;
    }

}

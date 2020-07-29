package work.lclpnet.corebase.event;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.event.entity.player.PlayerEvent;

/**
 * Called when a player quits the server
 */
public class PlayerQuitEvent extends PlayerEvent {

    private ITextComponent quitMessage;

    public PlayerQuitEvent(final PlayerEntity playerQuit, final ITextComponent quitMessage) {
        super(playerQuit);
        this.quitMessage = quitMessage;
    }

    /**
     * @return the quitMessage
     */
    public ITextComponent getQuitMessage() {
        return quitMessage;
    }

    /**
     * @param quitMessage the quitMessage to set
     */
    public void setQuitMessage(ITextComponent quitMessage) {
        this.quitMessage = quitMessage;
    }

}

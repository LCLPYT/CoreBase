package work.lclpnet.corebase.event.custom;

import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.ITextComponent;

/**
 * Called when a player joins a server
 */
public class PlayerJoinEvent extends PlayerEvent {

    private ITextComponent joinMessage;

    public PlayerJoinEvent(final PlayerEntity playerJoined, final ITextComponent joinMessage) {
        super(playerJoined);
        this.joinMessage = joinMessage;
    }

    /**
     * @return the joinMessage
     */
    public ITextComponent getJoinMessage() {
        return joinMessage;
    }

    /**
     * @param joinMessage the joinMessage to set
     */
    public void setJoinMessage(ITextComponent joinMessage) {
        this.joinMessage = joinMessage;
    }

}

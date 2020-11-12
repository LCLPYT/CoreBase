package work.lclpnet.corebase.event.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Cancelable;

/**
 * 
 * Called, when a player logs onto a server an is spawned at a position.
 * This event is {@link Cancelable}.
 * If the event was cancelled or if the result was not changed, the vanilla position will be used.
 * 
 * @author LCLP
 *
 */
@Cancelable
public class PlayerSpawnPositionEvent extends PlayerEvent {

	private Vector3d position;
	private float yaw, pitch;
	
	public PlayerSpawnPositionEvent(PlayerEntity player, Vector3d position, float yaw, float pitch) {
		super(player);
		this.position = position;
		this.yaw = yaw;
		this.pitch = pitch;
	}

	public Vector3d getPosition() {
		return position;
	}
	
	public float getYaw() {
		return yaw;
	}
	
	public float getPitch() {
		return pitch;
	}
	
	public void setPosition(Vector3d position) {
		this.position = position;
	}
	
	public void setYaw(float yaw) {
		this.yaw = yaw;
	}
	
	public void setPitch(float pitch) {
		this.pitch = pitch;
	}
	
}

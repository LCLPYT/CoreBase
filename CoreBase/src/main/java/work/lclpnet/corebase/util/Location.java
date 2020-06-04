package work.lclpnet.corebase.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class Location extends Vec3d{

	public final World world;
	
	public Location(Location other) {
		this(other.world, other);
	}
	
	public Location(World worldIn, Vec3d vecIn) {
		super(vecIn.x, vecIn.y, vecIn.z);
		this.world = worldIn;
	}
	
	public Location(World worldIn, double xIn, double yIn, double zIn) {
		super(xIn, yIn, zIn);
		this.world = worldIn;
	}
	
	public World getWorld() {
		return world;
	}
	
	public BlockPos toBlockPos() {
		return new BlockPos(this);
	}
	
	public BlockLocation toBlockLocation() {
		return new BlockLocation(world, this);
	}
	
	public LocationRotation withRotation(float yawIn, float pitchIn) {
		return new LocationRotation(this, yawIn, pitchIn);
	}

}

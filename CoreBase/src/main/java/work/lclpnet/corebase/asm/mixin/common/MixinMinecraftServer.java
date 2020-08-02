package work.lclpnet.corebase.asm.mixin.common;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import work.lclpnet.corebase.event.custom.ServerReloadedEvent;

@Mixin(MinecraftServer.class)
public class MixinMinecraftServer {

	@Inject(
			method = "Lnet/minecraft/server/MinecraftServer;reload()V",
			at = @At("TAIL")
			)
	public void onReload(CallbackInfo ci) {
		MinecraftServer ms = (MinecraftServer) ((Object) this);
		MinecraftForge.EVENT_BUS.post(new ServerReloadedEvent(ms));
	}
	
}

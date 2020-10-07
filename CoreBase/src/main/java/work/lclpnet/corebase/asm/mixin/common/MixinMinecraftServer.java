package work.lclpnet.corebase.asm.mixin.common;

import java.util.concurrent.CompletableFuture;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import work.lclpnet.corebase.event.custom.ServerReloadedEvent;

@Mixin(MinecraftServer.class)
public class MixinMinecraftServer {

	@Inject(
			method = "Lnet/minecraft/server/MinecraftServer;func_240780_a_(Ljava/util/Collection;)Ljava/util/concurrent/CompletableFuture;",
			at = @At("TAIL"),
			remap = false
			)
	public void onReload(CallbackInfoReturnable<CompletableFuture<?>> ci) {
		MinecraftServer ms = (MinecraftServer) ((Object) this);
		MinecraftForge.EVENT_BUS.post(new ServerReloadedEvent(ms));
	}
	
}

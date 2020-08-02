package work.lclpnet.corebase.asm.mixin.common;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.ServerPlayNetHandler;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.MinecraftForge;
import work.lclpnet.corebase.event.custom.PlayerQuitEvent;

@Mixin(ServerPlayNetHandler.class)
public class MixinServerPlayNetHandler {

	@Shadow
	public ServerPlayerEntity player;
	
	@Redirect(
			method = "Lnet/minecraft/network/play/ServerPlayNetHandler;onDisconnect(Lnet/minecraft/util/text/ITextComponent;)V", 
			at = @At(value = "INVOKE", target = "Lnet/minecraft/server/management/PlayerList;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V")
			)
	public void onSendMessage(PlayerList list, ITextComponent msg) {
		ITextComponent quitMessage = (new TranslationTextComponent("multiplayer.player.left", this.player.getDisplayName())).applyTextStyle(TextFormatting.YELLOW);
        PlayerQuitEvent event = new PlayerQuitEvent(player, quitMessage);
        MinecraftForge.EVENT_BUS.post(event);
        quitMessage = event.getQuitMessage();
		if(quitMessage != null) list.sendMessage(quitMessage);
	}

}

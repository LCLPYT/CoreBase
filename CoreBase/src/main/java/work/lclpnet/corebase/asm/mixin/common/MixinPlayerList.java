package work.lclpnet.corebase.asm.mixin.common;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.authlib.GameProfile;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.NetworkManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.server.management.PlayerProfileCache;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.MinecraftForge;
import work.lclpnet.corebase.event.custom.PlayerJoinEvent;

@Mixin(PlayerList.class)
public class MixinPlayerList {

	@Shadow
	@Final
	private MinecraftServer server;
	
	@Shadow
	public void sendMessage(ITextComponent component) {}
	
	@Redirect(
			method = "Lnet/minecraft/server/management/PlayerList;initializeConnectionToPlayer(Lnet/minecraft/network/NetworkManager;Lnet/minecraft/entity/player/ServerPlayerEntity;)V",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/server/management/PlayerList;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V")
			)
	public void onSendMessage(PlayerList list, ITextComponent itextcomponent) {
		// do nothing
	}

	@Inject(
			method = "Lnet/minecraft/server/management/PlayerList;initializeConnectionToPlayer(Lnet/minecraft/network/NetworkManager;Lnet/minecraft/entity/player/ServerPlayerEntity;)V",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/server/management/PlayerList;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V", shift = Shift.AFTER)
			)
	public void afterSendMessage(NetworkManager netManager, ServerPlayerEntity playerIn, CallbackInfo ci) {
		GameProfile gameprofile = playerIn.getGameProfile();
		PlayerProfileCache playerprofilecache = this.server.getPlayerProfileCache();
		GameProfile gameprofile1 = playerprofilecache.getProfileByUUID(gameprofile.getId());
		String s = gameprofile1 == null ? gameprofile.getName() : gameprofile1.getName();

		ITextComponent itextcomponent;
		if (playerIn.getGameProfile().getName().equalsIgnoreCase(s)) {
			itextcomponent = new TranslationTextComponent("multiplayer.player.joined", playerIn.getDisplayName());
		} else {
			itextcomponent = new TranslationTextComponent("multiplayer.player.joined.renamed", playerIn.getDisplayName(), s);
		}

        PlayerJoinEvent event = new PlayerJoinEvent(playerIn, itextcomponent.applyTextStyle(TextFormatting.YELLOW));
        MinecraftForge.EVENT_BUS.post(event);
        itextcomponent = event.getJoinMessage();

		if(itextcomponent != null) {
			this.sendMessage(itextcomponent);
			playerIn.sendMessage(itextcomponent); //Is this necessary? 
		}
	}
	
}

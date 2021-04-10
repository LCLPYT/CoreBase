package work.lclpnet.corebase.asm.mixin.common;

import java.util.UUID;

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
import net.minecraft.util.Util;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.IFormattableTextComponent;
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
	public void func_232641_a_(ITextComponent msg, ChatType type, UUID senderUuid) {}

	@Redirect(
			method = "initializeConnectionToPlayer(Lnet/minecraft/network/NetworkManager;Lnet/minecraft/entity/player/ServerPlayerEntity;)V",
			at = @At(
					value = "INVOKE", 
					target = "Lnet/minecraft/server/management/PlayerList;func_232641_a_(Lnet/minecraft/util/text/ITextComponent;Lnet/minecraft/util/text/ChatType;Ljava/util/UUID;)V",
							remap = false
					)
			)
	public void onSendMessage(PlayerList list, ITextComponent itextcomponent, ChatType type, UUID senderUuid) {
		// do nothing
	}

	@Inject(
			method = "initializeConnectionToPlayer(Lnet/minecraft/network/NetworkManager;Lnet/minecraft/entity/player/ServerPlayerEntity;)V",
			at = @At(
					value = "INVOKE", 
					target = "Lnet/minecraft/server/management/PlayerList;func_232641_a_(Lnet/minecraft/util/text/ITextComponent;Lnet/minecraft/util/text/ChatType;Ljava/util/UUID;)V",
							remap = false,
							shift = Shift.AFTER
					)
			)
	public void afterSendMessage(NetworkManager netManager, ServerPlayerEntity playerIn, CallbackInfo ci) {
		GameProfile gameprofile = playerIn.getGameProfile();
		PlayerProfileCache playerprofilecache = this.server.getPlayerProfileCache();
		GameProfile gameprofile1 = playerprofilecache.getProfileByUUID(gameprofile.getId());
		String s = gameprofile1 == null ? gameprofile.getName() : gameprofile1.getName();

		IFormattableTextComponent itextcomponent;
		if (playerIn.getGameProfile().getName().equalsIgnoreCase(s)) {
			itextcomponent = new TranslationTextComponent("multiplayer.player.joined", playerIn.getDisplayName());
		} else {
			itextcomponent = new TranslationTextComponent("multiplayer.player.joined.renamed", playerIn.getDisplayName(), s);
		}

		PlayerJoinEvent event = new PlayerJoinEvent(playerIn, itextcomponent.mergeStyle(TextFormatting.YELLOW));
		MinecraftForge.EVENT_BUS.post(event);

		ITextComponent itc = event.getJoinMessage();
		if(itc != null) {
			this.func_232641_a_(itc, ChatType.SYSTEM, Util.DUMMY_UUID);
			playerIn.sendMessage(itc, Util.DUMMY_UUID); //Is this necessary? 
		}
	}

}

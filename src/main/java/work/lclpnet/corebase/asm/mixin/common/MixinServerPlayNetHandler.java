package work.lclpnet.corebase.asm.mixin.common;

import java.util.List;
import java.util.UUID;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.ServerPlayNetHandler;
import net.minecraft.network.play.client.CUpdateSignPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import work.lclpnet.corebase.event.custom.PlayerQuitEvent;
import work.lclpnet.corebase.event.custom.SignChangeEvent;

@Mixin(ServerPlayNetHandler.class)
public class MixinServerPlayNetHandler {

	@Shadow
	public ServerPlayerEntity player;
	@Shadow
	@Final
	private MinecraftServer server;

	// PlayerQuitEvent start
	@Redirect(
			method = "Lnet/minecraft/network/play/ServerPlayNetHandler;onDisconnect(Lnet/minecraft/util/text/ITextComponent;)V", 
			at = @At(
					value = "INVOKE", 
					target = "Lnet/minecraft/server/management/PlayerList;func_232641_a_("
							+ "Lnet/minecraft/util/text/ITextComponent;"
							+ "Lnet/minecraft/util/text/ChatType;"
							+ "Ljava/util/UUID;"
							+ ")V",
							remap = false
					)
			)
	public void onQuitMessage(PlayerList list, ITextComponent msg, ChatType type, UUID uuid) {
		ITextComponent quitMessage = new TranslationTextComponent("multiplayer.player.left", this.player.getDisplayName()).mergeStyle(TextFormatting.YELLOW);
		PlayerQuitEvent event = new PlayerQuitEvent(player, quitMessage);
		MinecraftForge.EVENT_BUS.post(event);
		quitMessage = event.getQuitMessage();
		if(quitMessage != null) list.func_232641_a_(quitMessage, ChatType.SYSTEM, Util.DUMMY_UUID);
	}

	// PlayerQuitEvent end

	// SignChangeEvent start

	@Inject(
			method = "Lnet/minecraft/network/play/ServerPlayNetHandler;func_244542_a("
					+ "Lnet/minecraft/network/play/client/CUpdateSignPacket;"
					+ "Ljava/util/List;"
					+ ")V",
					remap = false,
					at = @At(
							value = "INVOKE",
							target = "Ljava/util/List;size()I",
							remap = false
							),
					cancellable = true
			)
	public void onSetSign(CUpdateSignPacket packet, List<String> lines, CallbackInfo ci) {
		ServerWorld serverworld = this.player.getServerWorld();
		BlockPos pos = packet.getPosition();
		BlockState blockstate = serverworld.getBlockState(pos);

		SignChangeEvent event = new SignChangeEvent(serverworld, pos, blockstate, lines, player);
		MinecraftForge.EVENT_BUS.post(event);

		if(event.isCanceled()) {
			ci.cancel();
			return;
		}

		TileEntity tileentity = serverworld.getTileEntity(pos);
		if (!(tileentity instanceof SignTileEntity)) {
			return;
		}
		SignTileEntity signtileentity = (SignTileEntity) tileentity;

		ITextComponent[] componentLines = event.getComponentLines();

		for(int i = 0; i < lines.size(); ++i) {
			if(componentLines[i] != null) signtileentity.setText(i, componentLines[i]);
			else signtileentity.setText(i, new StringTextComponent(lines.get(i)));
		}

		signtileentity.markDirty();
		serverworld.notifyBlockUpdate(pos, blockstate, blockstate, 3);
		
		ci.cancel();
	}

	// SignChangeEvent end

}

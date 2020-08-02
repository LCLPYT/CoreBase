package work.lclpnet.corebase.asm.mixin.common;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.ServerPlayNetHandler;
import net.minecraft.network.play.client.CUpdateSignPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.util.math.BlockPos;
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
			at = @At(value = "INVOKE", target = "Lnet/minecraft/server/management/PlayerList;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V")
			)
	public void onSendMessage(PlayerList list, ITextComponent msg) {
		ITextComponent quitMessage = (new TranslationTextComponent("multiplayer.player.left", this.player.getDisplayName())).applyTextStyle(TextFormatting.YELLOW);
		PlayerQuitEvent event = new PlayerQuitEvent(player, quitMessage);
		MinecraftForge.EVENT_BUS.post(event);
		quitMessage = event.getQuitMessage();
		if(quitMessage != null) list.sendMessage(quitMessage);
	}
	
	// PlayerQuitEvent end
	
	// SignChangeEvent start

	@Redirect(
			method = "Lnet/minecraft/network/play/ServerPlayNetHandler;processUpdateSign(Lnet/minecraft/network/play/client/CUpdateSignPacket;)V",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/network/play/client/CUpdateSignPacket;getLines()[Ljava/lang/String;")
			)
	public String[] onProcessUpdateSign(CUpdateSignPacket packetIn) {
		ServerWorld serverworld = this.server.getWorld(this.player.dimension);
		BlockPos pos = packetIn.getPosition();
		SignTileEntity signtileentity = (SignTileEntity) serverworld.getTileEntity(pos);

		String[] astring = packetIn.getLines();
		ITextComponent[] fixedLines = new ITextComponent[astring.length];
		for(int i = 0; i < astring.length; ++i)
			fixedLines[i] = new StringTextComponent(TextFormatting.getTextWithoutFormattingCodes(astring[i]));

		SignChangeEvent event = new SignChangeEvent(serverworld, pos, serverworld.getBlockState(pos), fixedLines, player);
		MinecraftForge.EVENT_BUS.post(event);

		if(event.isCanceled()) fixedLines = new StringTextComponent[astring.length];
		else fixedLines = event.getLines();

		for(int i = 0; i < astring.length; ++i) {
			signtileentity.setText(i, fixedLines[i] == null ? new StringTextComponent(""): fixedLines[i]);
			System.out.println("SET " + i + fixedLines[i].toString());
		}

		return packetIn.getLines();
	}
	
	@Redirect(
			method = "Lnet/minecraft/network/play/ServerPlayNetHandler;processUpdateSign(Lnet/minecraft/network/play/client/CUpdateSignPacket;)V",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/tileentity/SignTileEntity;setText(ILnet/minecraft/util/text/ITextComponent;)V")
			)
	public void onSetText(SignTileEntity ste, int line, ITextComponent text) {
		// do nothing
	}
	
	// SignChangeEvent end

}

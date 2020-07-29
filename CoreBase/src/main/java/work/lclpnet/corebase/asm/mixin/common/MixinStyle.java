package work.lclpnet.corebase.asm.mixin.common;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;

@Mixin(Style.class)
public class MixinStyle {

	@Shadow
	private TextFormatting color;
	
	@Inject(method = "Lnet/minecraft/util/text/Style;getColor()Lnet/minecraft/util/text/TextFormatting;", at = @At("RETURN"), cancellable = true)
	public void onGetColor(CallbackInfoReturnable<TextFormatting> cir) {
		cir.setReturnValue(this.color);
		cir.cancel();
	}
	
}

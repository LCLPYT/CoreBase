package work.lclpnet.corebase.asm.mixin.common;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraftforge.common.MinecraftForge;
import work.lclpnet.corebase.event.custom.PlayerArmorStandManipulateEvent;

@Mixin(ArmorStandEntity.class)
public class MixinArmorStandEntity {

	@Inject(
			method = "Lnet/minecraft/entity/item/ArmorStandEntity;equipOrSwap("
					+ "Lnet/minecraft/entity/player/PlayerEntity;"
					+ "Lnet/minecraft/inventory/EquipmentSlotType;"
					+ "Lnet/minecraft/item/ItemStack;"
					+ "Lnet/minecraft/util/Hand;"
					+ ")Z",
					at = @At(
							value = "FIELD",
							target = "Lnet/minecraft/entity/player/PlayerAbilities;isCreativeMode:Z",
							opcode = Opcodes.GETFIELD
							), 
					cancellable = true
			)
	public void onArmorStandManipulate(PlayerEntity player, EquipmentSlotType slot, ItemStack is, Hand hand, CallbackInfoReturnable<Boolean> cir) {
		PlayerArmorStandManipulateEvent event = new PlayerArmorStandManipulateEvent(player, (ArmorStandEntity) ((Object) this));
		MinecraftForge.EVENT_BUS.post(event);
		if(event.isCanceled()) {
			cir.setReturnValue(true);
			cir.cancel();
		}
	}

}

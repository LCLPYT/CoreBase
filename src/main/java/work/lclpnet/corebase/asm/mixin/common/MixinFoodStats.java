package work.lclpnet.corebase.asm.mixin.common;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.FoodStats;
import net.minecraftforge.common.MinecraftForge;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import work.lclpnet.corebase.asm.type.IPlayerFoodStats;
import work.lclpnet.corebase.event.custom.FoodExhaustionLevelChangeEvent;
import work.lclpnet.corebase.event.custom.FoodLevelChangeEvent;
import work.lclpnet.corebase.event.custom.FoodSaturationLevelChangeEvent;

@Mixin(FoodStats.class)
public class MixinFoodStats implements IPlayerFoodStats {

    @Shadow
    private int foodLevel;
    @Shadow
    private float foodExhaustionLevel;
    @Shadow
    private float foodSaturationLevel;

    private PlayerEntity player;

    @Redirect(
            method = "setFoodLevel(I)V",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/util/FoodStats;foodLevel:I",
                    opcode = Opcodes.PUTFIELD
            )
    )
    public void onSetFoodLevel(FoodStats foodStats, int foodLevelIn) {
        toFoodLevelInternally(foodLevelIn);
    }

    @Redirect(
            method = "tick(Lnet/minecraft/entity/player/PlayerEntity;)V",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/util/FoodStats;foodLevel:I",
                    opcode = Opcodes.PUTFIELD
            )
    )
    public void onTickFoodLevel(FoodStats foodStats, int foodLevelIn) {
        toFoodLevelInternally(foodLevelIn);
    }

    @Redirect(
            method = "addStats(IF)V",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/util/FoodStats;foodLevel:I",
                    opcode = Opcodes.PUTFIELD
            )
    )
    public void onAddStatsFoodLevel(FoodStats foodStats, int foodLevelIn) {
        toFoodLevelInternally(foodLevelIn);
    }

    @Redirect(
            method = "addExhaustion(F)V",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/util/FoodStats;foodExhaustionLevel:F",
                    opcode = Opcodes.PUTFIELD
            )
    )
    public void onAddExhaustion(FoodStats foodStats, float exhaustionLevelIn) {
        toExhaustionLevelInternally(exhaustionLevelIn);
    }

    @Redirect(
            method = "tick(Lnet/minecraft/entity/player/PlayerEntity;)V",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/util/FoodStats;foodExhaustionLevel:F",
                    opcode = Opcodes.PUTFIELD
            )
    )
    public void onTickExhaustion(FoodStats foodStats, float exhaustionLevelIn) {
        toExhaustionLevelInternally(exhaustionLevelIn);
    }

    @Redirect(
            method = "addStats(IF)V",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/util/FoodStats;foodSaturationLevel:F",
                    opcode = Opcodes.PUTFIELD
            )
    )
    public void onAddStatsSaturation(FoodStats foodStats, float saturationLevelIn) {
        toSaturationLevelInternally(saturationLevelIn);
    }

    @Redirect(
            method = "tick(Lnet/minecraft/entity/player/PlayerEntity;)V",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/util/FoodStats;foodSaturationLevel:F",
                    opcode = Opcodes.PUTFIELD
            )
    )
    public void onTickSaturation(FoodStats foodStats, float saturationLevelIn) {
        toSaturationLevelInternally(saturationLevelIn);
    }

    private void toSaturationLevelInternally(float saturationLevelIn) {
        FoodSaturationLevelChangeEvent event = new FoodSaturationLevelChangeEvent(player, foodSaturationLevel, saturationLevelIn);
        MinecraftForge.EVENT_BUS.post(event);
        if (!event.isCanceled()) foodSaturationLevel = event.getToLevel();
    }

    private void toExhaustionLevelInternally(float exhaustionLevelIn) {
        FoodExhaustionLevelChangeEvent event = new FoodExhaustionLevelChangeEvent(player, foodExhaustionLevel, exhaustionLevelIn);
        MinecraftForge.EVENT_BUS.post(event);
        if (!event.isCanceled()) foodExhaustionLevel = event.getToLevel();
    }

    private void toFoodLevelInternally(int foodLevelIn) {
        FoodLevelChangeEvent event = new FoodLevelChangeEvent(player, foodLevel, foodLevelIn);
        MinecraftForge.EVENT_BUS.post(event);
        if (!event.isCanceled()) foodLevel = event.getToLevel();
    }

    @Override
    public void setPlayer(PlayerEntity player) {
        this.player = player;
    }

    @Override
    public PlayerEntity getPlayer() {
        return player;
    }

}

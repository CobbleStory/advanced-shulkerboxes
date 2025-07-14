package de.maxhenkel.shulkerbox.mixin;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static de.maxhenkel.shulkerbox.AdvancedShulkerboxesMod.BLOCK_SHULKER_REPOSITORY;

@Mixin(ShulkerBoxBlock.class)
public class ShulkerBoxBlockMixin {

    @Inject(method = "useWithoutItem", at = @At(value = "HEAD"), cancellable = true)
    public void useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult, CallbackInfoReturnable<InteractionResult> cir) {
        if (player instanceof ServerPlayer serverPlayer) {
            if (!BLOCK_SHULKER_REPOSITORY.canOpen(serverPlayer, blockPos)) {
                serverPlayer.sendSystemMessage(Component.literal("Veuillez patientez 5 secondes avant de réouvrir votre shulker !").withStyle(ChatFormatting.RED));
                cir.setReturnValue(InteractionResult.SUCCESS);
            } else {
                BLOCK_SHULKER_REPOSITORY.add(serverPlayer, blockPos);
            }
        }
    }
}

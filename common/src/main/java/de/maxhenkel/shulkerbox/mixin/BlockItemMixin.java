package de.maxhenkel.shulkerbox.mixin;

import de.maxhenkel.shulkerbox.AdvancedShulkerboxesMod;
import de.maxhenkel.shulkerbox.menu.AdvancedShulkerboxMenu;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static de.maxhenkel.shulkerbox.AdvancedShulkerboxesMod.ITEM_SHULKER_REPOSITORY;

@Mixin(BlockItem.class)
public abstract class BlockItemMixin {

    @Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
    public void useOn(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        if (!(getBlock() instanceof ShulkerBoxBlock)) return;

        if (!AdvancedShulkerboxesMod.CONFIG.sneakPlace.get()) return;

        if (context.getPlayer().isShiftKeyDown()) return;

        if (context.getItemInHand().getCount() != 1) return;

        if (context.getPlayer() instanceof ServerPlayer serverPlayer) {
            ItemStack itemInHand = context.getItemInHand();

            if (ITEM_SHULKER_REPOSITORY.canOpen(serverPlayer, itemInHand)) {
                AdvancedShulkerboxMenu.open(serverPlayer, context.getItemInHand());
                ITEM_SHULKER_REPOSITORY.add(serverPlayer, itemInHand);
            } else {
                serverPlayer.sendSystemMessage(Component.literal("Veuillez patientez 5 secondes avant de réouvrir votre shulker !").withStyle(ChatFormatting.RED));
            }
        }

        cir.setReturnValue(InteractionResult.SUCCESS);
    }


    @Shadow
    public abstract Block getBlock();
}
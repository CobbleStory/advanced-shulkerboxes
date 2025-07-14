package de.maxhenkel.shulkerbox.cache;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public record PlayerShulkerBox(ServerPlayer player, ItemStack stack, long timestamp) {

}

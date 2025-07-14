package de.maxhenkel.shulkerbox.cache.item;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public record ItemShulkerBox(ServerPlayer player, ItemStack stack, long timestamp) {

}

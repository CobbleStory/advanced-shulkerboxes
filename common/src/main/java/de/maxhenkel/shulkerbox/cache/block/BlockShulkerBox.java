package de.maxhenkel.shulkerbox.cache.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;

public record BlockShulkerBox(ServerPlayer player, BlockPos pos, long timestamp) {

}
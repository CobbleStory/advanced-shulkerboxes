package de.maxhenkel.shulkerbox.cache;

import net.minecraft.server.level.ServerPlayer;

import java.util.List;

public interface ShulkerBoxRepository<T, D> {

    void add(ServerPlayer player, D data);

    void remove(ServerPlayer player, D data);

    List<T> getPlayerShulkers(ServerPlayer player);

    boolean canOpen(ServerPlayer player, D data);
}

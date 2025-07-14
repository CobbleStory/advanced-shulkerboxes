package de.maxhenkel.shulkerbox.cache.block;

import de.maxhenkel.shulkerbox.cache.ShulkerBoxRepository;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class BlockShulkerRepository implements ShulkerBoxRepository<BlockShulkerBox, BlockPos> {

    private final List<BlockShulkerBox> cachedShulkers = new ArrayList<>();


    @Override
    public void add(ServerPlayer player, BlockPos pos) {
        long timestamp = Instant.now().plus(5, ChronoUnit.SECONDS).getEpochSecond();
        cachedShulkers.add(new BlockShulkerBox(player, pos, timestamp));
    }


    @Override
    public void remove(ServerPlayer player, BlockPos pos) {
        cachedShulkers.removeIf(shulker -> shulker.player().equals(player) && shulker.pos().equals(pos));
    }


    @Override
    public List<BlockShulkerBox> getPlayerShulkers(ServerPlayer player) {
        return cachedShulkers.stream()
                .filter(shulker -> shulker.player().equals(player))
                .toList();
    }


    @Override
    public boolean canOpen(ServerPlayer player, BlockPos pos) {
        Instant currentInstant = Instant.now();

        for (BlockShulkerBox cachedShulker : getPlayerShulkers(player)) {
            Instant instant = Instant.ofEpochSecond(cachedShulker.timestamp());
            boolean isExpired = currentInstant.isAfter(instant);

            if (!pos.equals(cachedShulker.pos())) continue;
            if (!isExpired) return false;

            remove(player, pos);
            return true;
        }

        return true;
    }
}

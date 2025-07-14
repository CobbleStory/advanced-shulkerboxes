package de.maxhenkel.shulkerbox.cache;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class ShulkerBoxRepository {

    private final List<PlayerShulkerBox> cachedShulkers = new ArrayList<>();


    public void add(ServerPlayer player, ItemStack stack) {
        long timestamp = Instant.now().plus(5, ChronoUnit.SECONDS).getEpochSecond();
        cachedShulkers.add(new PlayerShulkerBox(player, stack, timestamp));
    }


    private void remove(ServerPlayer player, ItemStack stack) {
        cachedShulkers.removeIf(shulker -> shulker.player().equals(player) && shulker.stack().equals(stack));
    }


    private List<PlayerShulkerBox> getPlayerShulkerBoxes(ServerPlayer player) {
        return cachedShulkers.stream()
                .filter(shulker -> shulker.player().equals(player))
                .toList();
    }


    public Boolean canOpen(ServerPlayer player, ItemStack stack) {
        Instant currentInstant = Instant.now();

        for (PlayerShulkerBox cachedShulker : getPlayerShulkerBoxes(player)) {
            if (!stack.equals(cachedShulker.stack())) continue;

            Instant instant = Instant.ofEpochSecond(cachedShulker.timestamp());
            if (currentInstant.isAfter(instant)) return false;

            remove(player, stack);
            return true;
        }

        return true;
    }
}

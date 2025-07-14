package de.maxhenkel.shulkerbox.cache.item;

import de.maxhenkel.shulkerbox.cache.ShulkerBoxRepository;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class ItemShulkerRepository implements ShulkerBoxRepository<ItemShulkerBox, ItemStack> {

    private final List<ItemShulkerBox> cachedShulkers = new ArrayList<>();


    @Override
    public void add(ServerPlayer player, ItemStack stack) {
        long timestamp = Instant.now().plus(5, ChronoUnit.SECONDS).getEpochSecond();
        cachedShulkers.add(new ItemShulkerBox(player, stack, timestamp));
    }


    @Override
    public void remove(ServerPlayer player, ItemStack stack) {
        cachedShulkers.removeIf(shulker -> shulker.player().equals(player) && shulker.stack().equals(stack));
    }


    @Override
    public List<ItemShulkerBox> getPlayerShulkers(ServerPlayer player) {
        return cachedShulkers.stream()
                .filter(shulker -> shulker.player().equals(player))
                .toList();
    }


    @Override
    public boolean canOpen(ServerPlayer player, ItemStack stack) {
        Instant currentInstant = Instant.now();

        for (ItemShulkerBox cachedShulker : getPlayerShulkers(player)) {
            Instant instant = Instant.ofEpochSecond(cachedShulker.timestamp());
            boolean isExpired = currentInstant.isAfter(instant);

            if (!stack.equals(cachedShulker.stack())) continue;
            if (!isExpired) return false;

            remove(player, stack);
            return true;
        }

        return true;
    }
}

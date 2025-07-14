package de.maxhenkel.shulkerbox;

import de.maxhenkel.configbuilder.ConfigBuilder;
import de.maxhenkel.shulkerbox.cache.ShulkerBoxRepository;
import de.maxhenkel.shulkerbox.cache.block.BlockShulkerBox;
import de.maxhenkel.shulkerbox.cache.block.BlockShulkerRepository;
import de.maxhenkel.shulkerbox.cache.item.ItemShulkerBox;
import de.maxhenkel.shulkerbox.cache.item.ItemShulkerRepository;
import de.maxhenkel.shulkerbox.config.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;

public abstract class AdvancedShulkerboxesMod {

    public static final String MODID = "shulkerbox";
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public static Config CONFIG;

    public static ShulkerBoxRepository<ItemShulkerBox, ItemStack> ITEM_SHULKER_REPOSITORY =  new ItemShulkerRepository();
    public static ShulkerBoxRepository<BlockShulkerBox, BlockPos> BLOCK_SHULKER_REPOSITORY =  new BlockShulkerRepository();


    public void init() {
        CONFIG = ConfigBuilder.builder(Config::new).path(Path.of(".", "config", MODID).resolve("%s.properties".formatted(MODID))).build();
    }
}

package be.justekal.etoiles_universe.item;

import be.justekal.etoiles_universe.EtoilesUniverseMod;
import be.justekal.etoiles_universe.block.ModBlocks;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, EtoilesUniverseMod.MODID);

    // BlockItem pour le cucumber_block
    public static final RegistryObject<Item> CUCUMBER_BLOCK_ITEM = ITEMS.register("cucumber_block",
        () -> new BlockItem(ModBlocks.CUCUMBER_BLOCK.get(), new Item.Properties()));

    // BlockItem pour le cucumber_planks
    public static final RegistryObject<Item> CUCUMBER_PLANKS_ITEM = ITEMS.register("cucumber_planks",
        () -> new BlockItem(ModBlocks.CUCUMBER_PLANKS.get(), new Item.Properties()));

    // Ajoute ici tes futurs items !

     public static final RegistryObject<Item> STAR = ITEMS.register("star",
        () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> CUCUMBER_SLAB_ITEM = ITEMS.register("cucumber_slab",
        () -> new BlockItem(ModBlocks.CUCUMBER_SLAB.get(), new Item.Properties()));

    public static final RegistryObject<Item> CUCUMBER_STAIRS_ITEM = ITEMS.register("cucumber_stairs",
        () -> new BlockItem(ModBlocks.CUCUMBER_STAIRS.get(), new Item.Properties()));

    public static final RegistryObject<Item> CUCUMBER_DOOR_ITEM = ITEMS.register("cucumber_door",
        () -> new BlockItem(ModBlocks.CUCUMBER_DOOR.get(), new Item.Properties()));

    public static final RegistryObject<Item> CUCUMBER_FENCE_ITEM = ITEMS.register("cucumber_fence",
        () -> new BlockItem(ModBlocks.CUCUMBER_FENCE.get(), new Item.Properties()));

    public static final RegistryObject<Item> CUCUMBER_FENCE_GATE_ITEM = ITEMS.register("cucumber_fence_gate",
        () -> new BlockItem(ModBlocks.CUCUMBER_FENCE_GATE.get(), new Item.Properties()));

    public static final RegistryObject<Item> ETOILES_STICK = ITEMS.register("etoiles_stick",
        () -> new Item(new Item.Properties())
    );

    public static final RegistryObject<Item> PICKLE_INGOT = ITEMS.register("pickle_ingot",
        () -> new Item(new Item.Properties())
    );
}
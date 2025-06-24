package be.justekal.etoiles_universe.item;

import be.justekal.etoiles_universe.EtoilesUniverseMod;
import be.justekal.etoiles_universe.block.ModBlocks;
import be.justekal.etoiles_universe.entity.ModEntities;
import be.justekal.etoiles_universe.sounds.ModSounds;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, EtoilesUniverseMod.MODID);

    // BlockItem pour le cucumber_block
    public static final RegistryObject<Item> CUCUMBER_BLOCK_ITEM = ITEMS.register("cucumber_block",
        () -> new BlockItem(ModBlocks.CUCUMBER_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Item> CUCUMBER_PLANKS_ITEM = ITEMS.register("cucumber_planks",
        () -> new BlockItem(ModBlocks.CUCUMBER_PLANKS.get(), new Item.Properties()));

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

        //sign
    public static final RegistryObject<Item> CUCUMBER_SIGN_ITEM = ITEMS.register("cucumber_sign",
    () -> new SignItem(new Item.Properties().stacksTo(16),
        ModBlocks.CUCUMBER_SIGN.get(), ModBlocks.CUCUMBER_WALL_SIGN.get())
    );

    public static final RegistryObject<ForgeSpawnEggItem> ETOILES_SPAWN_EGG = ITEMS.register(
    "etoiles_spawn_egg",
    () -> new ForgeSpawnEggItem(
        ModEntities.ETOILES, 
        0x6aa84f, 
        0xffd966, 
        new Item.Properties()
    )
    );

     public static final RegistryObject<Item> STAR = ITEMS.register("star",
        () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ETOILES_STICK = ITEMS.register("etoiles_stick",
        () -> new Item(new Item.Properties())
    );

    public static final RegistryObject<Item> PICKLE_INGOT = ITEMS.register("pickle_ingot",
        () -> new Item(new Item.Properties())
    );

    // Disc
    public static final RegistryObject<Item> ETOILES_DIAMOND = ITEMS.register("etoiles_diamond",
    () -> new RecordItem(
        1,
        ModSounds.DIAMONDSETOILES,
        new Item.Properties().stacksTo(1).rarity(Rarity.RARE),
        4460
    )
    );

    public static final RegistryObject<Item> ETOILES_VAMOS_A_LA_PLAYA = ITEMS.register("etoiles_vamos_a_la_playa",
        () -> new RecordItem(
            1,
            ModSounds.ETOILES_VAMOS_A_LA_PLAYA,
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE),
            4520
        )
    );

    public static final RegistryObject<Item> REFLET_VIADUC = ITEMS.register("reflet_viaduc",
        () -> new RecordItem(
            1,
            ModSounds.REFLET_VIADUC,
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE),
            4960
        )
    );

    public static final RegistryObject<Item> ETOILES_SHAKE_IT_OFF = ITEMS.register("etoiles_shake_it_off",
        () -> new RecordItem(
            1,
            ModSounds.ETOILES_SHAKE_IT_OFF,
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE),
            4840
        )
    );

    public static final RegistryObject<Item> JE_NE_REGRETTE_RIEN = ITEMS.register("je_ne_regrette_rien",
        () -> new RecordItem(
            1,
            ModSounds.JE_NE_REGRETTE_RIEN,
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE),
            3240
        )
    );

    public static final RegistryObject<Item> AINSI_FONT_LES_MARIONETTES = ITEMS.register("ainsi_font_les_marionettes",
        () -> new RecordItem(
            1,
            ModSounds.AINSI_FONT_LES_MARIONETTES,
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE),
            1320
        )
    );

    public static final RegistryObject<Item> JOYEUX_ANNIVERSAIRE = ITEMS.register("joyeux_anniversaire",
        () -> new RecordItem(
            1,
            ModSounds.JOYEUX_ANNIVERSAIRE,
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE),
            4120
        )
    );

    public static final RegistryObject<Item> RICKS_ROLL = ITEMS.register("ricks_roll",
        () -> new RecordItem(
            1,
            ModSounds.RICKS_ROLL,
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE),
            4880
        )
    );


}
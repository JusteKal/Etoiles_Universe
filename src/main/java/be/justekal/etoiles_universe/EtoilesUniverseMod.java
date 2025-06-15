package be.justekal.etoiles_universe;

import be.justekal.etoiles_universe.block.ModBlocks;
import be.justekal.etoiles_universe.item.ModItems;
import be.justekal.etoiles_universe.painting.ModPaintings;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(EtoilesUniverseMod.MODID)
public class EtoilesUniverseMod
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "etoiles_universe";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "etoiles_universe" namespace
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    // Create a Deferred Register to hold Items which will all be registered under the "etoiles_universe" namespace
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "etoiles_universe" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    // Ajoute ce nouveau Creative Tab
    public static final RegistryObject<CreativeModeTab> ETOILES_UNIVERSE_TAB = CREATIVE_MODE_TABS.register("etoiles_universe_tab", () ->
        CreativeModeTab.builder()
            .title(net.minecraft.network.chat.Component.translatable("itemGroup.etoiles_universe_tab"))
            .icon(() -> ModItems.STAR.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(ModItems.CUCUMBER_BLOCK_ITEM.get());
                output.accept(ModItems.CUCUMBER_PLANKS_ITEM.get()); // Ajoute les planches ici
                output.accept(ModItems.CUCUMBER_SLAB_ITEM.get());
                output.accept(ModItems.CUCUMBER_STAIRS_ITEM.get());
                output.accept(ModItems.CUCUMBER_DOOR_ITEM.get());
                output.accept(ModItems.CUCUMBER_FENCE_ITEM.get());
                output.accept(ModItems.CUCUMBER_FENCE_GATE_ITEM.get());
                output.accept(ModItems.ETOILES_STICK.get());
                output.accept(ModItems.PICKLE_INGOT.get());
            })
            .build()
    );

    public EtoilesUniverseMod(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();

        modEventBus.addListener(this::commonSetup);

        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);

        ModPaintings.PAINTINGS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }

    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTab() == ETOILES_UNIVERSE_TAB.get()) {
            event.accept(ModItems.CUCUMBER_BLOCK_ITEM.get());
            event.accept(ModItems.CUCUMBER_PLANKS_ITEM.get()); // Ajoute les planches ici
            event.accept(ModItems.CUCUMBER_SLAB_ITEM.get());
            event.accept(ModItems.CUCUMBER_STAIRS_ITEM.get());
            event.accept(ModItems.CUCUMBER_DOOR_ITEM.get());
            event.accept(ModItems.CUCUMBER_FENCE_ITEM.get());
            event.accept(ModItems.CUCUMBER_FENCE_GATE_ITEM.get());
        }
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(ModItems.CUCUMBER_BLOCK_ITEM.get());
            event.accept(ModItems.CUCUMBER_PLANKS_ITEM.get());
            event.accept(ModItems.CUCUMBER_SLAB_ITEM.get());
            event.accept(ModItems.CUCUMBER_STAIRS_ITEM.get());
            event.accept(ModItems.CUCUMBER_DOOR_ITEM.get());
            event.accept(ModItems.CUCUMBER_FENCE_ITEM.get());
            event.accept(ModItems.CUCUMBER_FENCE_GATE_ITEM.get());
        }
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
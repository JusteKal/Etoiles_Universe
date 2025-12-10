package be.justekal.etoiles_universe;

import be.justekal.etoiles_universe.block.ModBlocks;
import be.justekal.etoiles_universe.block.custom.ModWoodTypes;
import be.justekal.etoiles_universe.block.entity.ModBlockEntities;
import be.justekal.etoiles_universe.client.renderer.EtoilesBedRenderer;
import be.justekal.etoiles_universe.effect.ModEffects;
import be.justekal.etoiles_universe.entity.ModEntities;
import be.justekal.etoiles_universe.entity.client.EtoilesEntityRenderer;
import be.justekal.etoiles_universe.entity.custom.EtoilesEntity;
import be.justekal.etoiles_universe.gamerule.ModGameRules;
import be.justekal.etoiles_universe.item.ModItems;
import be.justekal.etoiles_universe.painting.ModPaintings;
import be.justekal.etoiles_universe.sounds.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
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
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

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
                output.accept(ModItems.CUCUMBER_SIGN_ITEM.get());
                output.accept(ModItems.ETOILES_STICK.get());
                output.accept(ModItems.PICKLE_INGOT.get());
                output.accept(ModItems.ETOILES_SPAWN_EGG.get());
                output.accept(ModItems.ETOILES_PLUSH.get());
                output.accept(ModItems.ETOILES_STATUE.get());
                output.accept(ModItems.ETOILES_BED.get());
                output.accept(ModItems.ETOILES_NECKLACE.get());
                // Armor
                output.accept(ModItems.ETOILES_HELMET.get());
                output.accept(ModItems.ETOILES_CHESTPLATE.get());
                output.accept(ModItems.ETOILES_LEGGINGS.get());
                output.accept(ModItems.ETOILES_BOOTS.get());
                // Diabetes items
                output.accept(ModItems.INSULIN.get());
                output.accept(ModItems.DIABETES_CURE.get());
                output.accept(ModItems.SUGAR_CUBE.get());
                // Disc
                output.accept(ModItems.ETOILES_DIAMOND.get());
                output.accept(ModItems.ETOILES_SHAKE_IT_OFF.get());
                output.accept(ModItems.JE_NE_REGRETTE_RIEN.get());
                output.accept(ModItems.AINSI_FONT_LES_MARIONETTES.get());
                output.accept(ModItems.JOYEUX_ANNIVERSAIRE.get());
                output.accept(ModItems.RICKS_ROLL.get());
                output.accept(ModItems.REFLET_VIADUC.get()); 
                output.accept(ModItems.ETOILES_VAMOS_A_LA_PLAYA.get());
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
        ModSounds.SOUNDS.register(modEventBus);
        ModBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        ModEntities.ENTITIES.register(modEventBus);
        ModEffects.register(modEventBus);

        // Enregistre les gamerules
        ModGameRules.register();

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(be.justekal.etoiles_universe.painting.PaintingPlaceHandler.class);

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
        public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.ETOILES.get(), EtoilesEntityRenderer::new);
        }

        @SubscribeEvent
        public static void onEntityAttributeCreate(EntityAttributeCreationEvent event) {
        event.put(ModEntities.ETOILES.get(), EtoilesEntity.createAttributes().build());
        }

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            WoodType.register(ModWoodTypes.CUCUMBER);

            // Enregistre le renderer pour les panneaux
            event.enqueueWork(() -> {
                BlockEntityRenderers.register(ModBlockEntities.CUCUMBER_SIGN.get(), SignRenderer::new);
                BlockEntityRenderers.register(ModBlockEntities.ETOILES_BED.get(), EtoilesBedRenderer::new);

                // Ajoute la texture de ton panneau à la feuille de sprites
                Sheets.addWoodType(ModWoodTypes.CUCUMBER); 
            });
        }
    }
}
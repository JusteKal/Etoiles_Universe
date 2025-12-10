package be.justekal.etoiles_universe.gamerule;

import net.minecraft.world.level.GameRules;

public class ModGameRules {
    public static GameRules.Key<GameRules.BooleanValue> ENABLE_DIABETES;

    public static void register() {
        ENABLE_DIABETES = GameRules.register("etoilesDiabetesEnabled", 
            GameRules.Category.PLAYER, 
            GameRules.BooleanValue.create(true));
    }
}

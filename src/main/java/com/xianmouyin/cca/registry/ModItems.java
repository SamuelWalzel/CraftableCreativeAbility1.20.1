package com.xianmouyin.cca.registry;

import com.xianmouyin.cca.Utils;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModItems {
    private ModItems() {}

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    // Muss "ability_core" heißen, damit assets/models/recipes passen
    public static final RegistryObject<Item> ABILITY_CORE =
            ITEMS.register("ability_core", () -> new AbilityCoreItem(
                    new Item.Properties()
                            .stacksTo(64)
                            .fireResistant()
                            .rarity(Rarity.EPIC)
            ));

    private static class AbilityCoreItem extends Item {
        private AbilityCoreItem(Properties props) { super(props); }
        @Override
        public boolean isFoil(ItemStack stack) { return true; }
    }
}

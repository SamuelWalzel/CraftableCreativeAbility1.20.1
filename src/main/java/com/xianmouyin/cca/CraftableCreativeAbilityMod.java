package com.xianmouyin.cca;

import com.xianmouyin.cca.registry.ModItems;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Utils.MOD_ID)
public class CraftableCreativeAbilityMod {

	public CraftableCreativeAbilityMod() {
		IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
		ModItems.ITEMS.register(modBus);

		// Optional: Item im Creative-Inventar anzeigen
		modBus.addListener(this::addToCreativeTabs);
	}

	private void addToCreativeTabs(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
			event.accept(ModItems.ABILITY_CORE);
		}
	}
}

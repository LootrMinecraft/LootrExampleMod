package noobanidus.mods.lootrexamplemod.fabric;

import net.fabricmc.api.ModInitializer;
import noobanidus.mods.lootrexamplemod.api.LootrExampleModAPI;
import noobanidus.mods.lootrexamplemod.fabric.impl.LootrExampleModAPIImpl;
import noobanidus.mods.lootrexamplemod.fabric.init.ModBlockEntities;
import noobanidus.mods.lootrexamplemod.fabric.init.ModBlocks;
import noobanidus.mods.lootrexamplemod.fabric.init.ModItems;
import noobanidus.mods.lootrexamplemod.fabric.init.ModTabs;

public class LootrExampleMod implements ModInitializer {
  @Override
  public void onInitialize() {
    LootrExampleModAPI.INSTANCE = new LootrExampleModAPIImpl();

    ModBlocks.registerBlocks();
    ModBlockEntities.registerBlockEntities();
    ModItems.registerItems();
    ModTabs.registerTabs();
  }
}

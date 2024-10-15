package noobanidus.mods.lootrexamplemod.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import noobanidus.mods.lootrexamplemod.api.LootrExampleModAPI;
import noobanidus.mods.lootrexamplemod.neoforge.impl.LootrExampleModAPIImpl;
import noobanidus.mods.lootrexamplemod.neoforge.init.ModBlockEntities;
import noobanidus.mods.lootrexamplemod.neoforge.init.ModBlocks;
import noobanidus.mods.lootrexamplemod.neoforge.init.ModItems;
import noobanidus.mods.lootrexamplemod.neoforge.init.ModTabs;

@Mod("lootrexamplemod")
public class LootrExampleMod {
  public LootrExampleMod(ModContainer container, IEventBus modBus) {
    LootrExampleModAPI.INSTANCE = new LootrExampleModAPIImpl();

    ModBlocks.register(modBus);
    ModItems.register(modBus);
    ModBlockEntities.register(modBus);
    ModTabs.register(modBus);
  }
}

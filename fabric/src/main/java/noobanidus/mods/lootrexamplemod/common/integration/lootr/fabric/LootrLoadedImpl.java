package noobanidus.mods.lootrexamplemod.common.integration.lootr.fabric;

import net.fabricmc.loader.api.FabricLoader;

public class LootrLoadedImpl {
  public static boolean isLootrLoaded() {
    return FabricLoader.getInstance().isModLoaded("lootr");
  }
}

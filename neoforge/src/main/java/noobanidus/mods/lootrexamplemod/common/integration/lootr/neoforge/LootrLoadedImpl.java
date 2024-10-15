package noobanidus.mods.lootrexamplemod.common.integration.lootr.neoforge;

import net.neoforged.fml.ModList;

public class LootrLoadedImpl {
  public static boolean isLootrLoaded() {
    return ModList.get().isLoaded("lootr");
  }
}

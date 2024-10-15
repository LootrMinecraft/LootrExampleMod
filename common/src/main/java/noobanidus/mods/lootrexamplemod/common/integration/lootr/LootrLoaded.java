package noobanidus.mods.lootrexamplemod.common.integration.lootr;

import dev.architectury.injectables.annotations.ExpectPlatform;
import org.apache.commons.lang3.NotImplementedException;

public class LootrLoaded {
  @ExpectPlatform
  public static boolean isLootrLoaded () {
    throw new NotImplementedException("Must be implemented by subclasses.");
  }
}

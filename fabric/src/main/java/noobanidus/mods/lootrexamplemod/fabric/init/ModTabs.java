package noobanidus.mods.lootrexamplemod.fabric.init;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import noobanidus.mods.lootrexamplemod.api.LootrExampleModAPI;

public class ModTabs {
  public static final CreativeModeTab EXAMPLE_TAB = CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
      .title(Component.translatable("itemGroup.lootrexamplemod.lootrexamplemod"))
      .icon(() -> new ItemStack(ModBlocks.CHEST))
      .displayItems((p, output) -> {
        output.accept(ModBlocks.CHEST);
      }).build();

  public static void registerTabs() {
    Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, LootrExampleModAPI.rl("lootrexamplemod"), EXAMPLE_TAB);
  }
}

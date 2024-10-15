package noobanidus.mods.lootrexamplemod.fabric.init;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import noobanidus.mods.lootrexamplemod.api.LootrExampleModAPI;
import noobanidus.mods.lootrexamplemod.common.item.ExampleChestItem;

public class ModItems {
  public static final ExampleChestItem CHEST = new ExampleChestItem(ModBlocks.CHEST, new Item.Properties());

  public static void registerItems() {
    Registry.register(BuiltInRegistries.ITEM, LootrExampleModAPI.rl("example_chest"), CHEST);
  }
}

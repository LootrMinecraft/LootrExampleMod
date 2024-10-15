package noobanidus.mods.lootrexamplemod.neoforge.init;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import noobanidus.mods.lootrexamplemod.api.LootrExampleModAPI;
import noobanidus.mods.lootrexamplemod.common.item.ExampleChestItem;

public class ModItems {
  private static final DeferredRegister<Item> REGISTER = DeferredRegister.create(BuiltInRegistries.ITEM, LootrExampleModAPI.MODID);

  public static final DeferredHolder<Item, ExampleChestItem> EXAMPLE_CHEST = REGISTER.register("example_chest", () -> new ExampleChestItem(ModBlocks.EXAMPLE_CHEST.get(), new Item.Properties()));

  public static void register(IEventBus bus) {
    REGISTER.register(bus);
  }
}

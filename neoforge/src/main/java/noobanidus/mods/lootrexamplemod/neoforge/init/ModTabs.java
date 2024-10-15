package noobanidus.mods.lootrexamplemod.neoforge.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import noobanidus.mods.lootrexamplemod.api.LootrExampleModAPI;

public class ModTabs {
  private static final DeferredRegister<CreativeModeTab> REGISTER = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, LootrExampleModAPI.MODID);

  public static final DeferredHolder<CreativeModeTab, CreativeModeTab> LOOTR_EXAMPLE_MOD = REGISTER.register("lootrexamplemod", () -> CreativeModeTab.builder()
      .title(Component.translatable("itemGroup.lootrexamplemod"))
      .icon(() -> new ItemStack(ModItems.EXAMPLE_CHEST.get()))
      .displayItems((p, output) -> {
        output.accept(ModBlocks.EXAMPLE_CHEST.get());
      }).build());

  public static void register(IEventBus bus) {
    REGISTER.register(bus);
  }
}

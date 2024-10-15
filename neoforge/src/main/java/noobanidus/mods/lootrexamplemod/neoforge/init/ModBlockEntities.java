package noobanidus.mods.lootrexamplemod.neoforge.init;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import noobanidus.mods.lootrexamplemod.api.LootrExampleModAPI;
import noobanidus.mods.lootrexamplemod.common.block.entity.ExampleChestBlockEntity;

public class ModBlockEntities {
  private static final DeferredRegister<BlockEntityType<?>> REGISTER = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, LootrExampleModAPI.MODID);

  public static void register(IEventBus bus) {
    REGISTER.register(bus);
  }

  public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ExampleChestBlockEntity>> EXAMPLE_CHEST = REGISTER.register("example_chest", () -> BlockEntityType.Builder.of(ExampleChestBlockEntity::new, ModBlocks.EXAMPLE_CHEST.get()).build(null));
}

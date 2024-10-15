package noobanidus.mods.lootrexamplemod.fabric.init;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import noobanidus.mods.lootrexamplemod.api.LootrExampleModAPI;
import noobanidus.mods.lootrexamplemod.common.block.entity.ExampleChestBlockEntity;

public class ModBlockEntities {
  public static final BlockEntityType<ExampleChestBlockEntity> EXAMPLE_CHEST = BlockEntityType.Builder.of(ExampleChestBlockEntity::new, ModBlocks.CHEST).build(null);

  public static void registerBlockEntities() {
    Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, LootrExampleModAPI.rl("example_chest"), EXAMPLE_CHEST);
  }
}

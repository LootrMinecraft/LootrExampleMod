package noobanidus.mods.lootrexamplemod.fabric.init;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import noobanidus.mods.lootrexamplemod.api.LootrExampleModAPI;
import noobanidus.mods.lootrexamplemod.common.block.ExampleChestBlock;

public class ModBlocks {
  public static final ExampleChestBlock CHEST = new ExampleChestBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHEST).strength(2.5f));

  public static void registerBlocks() {
    Registry.register(BuiltInRegistries.BLOCK, LootrExampleModAPI.rl("example_chest"), CHEST);
  }
}

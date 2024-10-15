package noobanidus.mods.lootrexamplemod.neoforge.init;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import noobanidus.mods.lootrexamplemod.api.LootrExampleModAPI;
import noobanidus.mods.lootrexamplemod.common.block.ExampleChestBlock;

public class ModBlocks {
  private static final DeferredRegister<Block> REGISTER = DeferredRegister.create(BuiltInRegistries.BLOCK, LootrExampleModAPI.MODID);

  public static void register(IEventBus bus) {
    REGISTER.register(bus);
  }

  public static final DeferredHolder<Block, ExampleChestBlock> EXAMPLE_CHEST = REGISTER.register("example_chest", () -> new ExampleChestBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHEST).strength(2.5f)));
}

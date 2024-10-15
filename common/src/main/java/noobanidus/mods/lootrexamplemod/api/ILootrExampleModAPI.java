package noobanidus.mods.lootrexamplemod.api;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import noobanidus.mods.lootrexamplemod.common.block.entity.ExampleChestBlockEntity;

/**
 * Loader-agnostic API primarily used to access Block, Item and BlockEntity singletons.
 *
 * Confer {@link noobanidus.mods.lootrexamplemod.fabric.impl.LootrExampleModAPIImpl} and {@link noobanidus.mods.lootrexamplemod.neoforge.impl.LootrExampleModAPIImpl} for actual implementations.
 */
public interface ILootrExampleModAPI {
  Block getExampleChestBlock();

  BlockEntityType<ExampleChestBlockEntity> getExampleChestBlockEntity();

  Item getExampleChestBlockItem();
}

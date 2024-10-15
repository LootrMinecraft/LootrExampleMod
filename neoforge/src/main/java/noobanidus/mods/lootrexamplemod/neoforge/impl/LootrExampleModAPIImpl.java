package noobanidus.mods.lootrexamplemod.neoforge.impl;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import noobanidus.mods.lootrexamplemod.api.ILootrExampleModAPI;
import noobanidus.mods.lootrexamplemod.common.block.entity.ExampleChestBlockEntity;
import noobanidus.mods.lootrexamplemod.neoforge.init.ModBlockEntities;
import noobanidus.mods.lootrexamplemod.neoforge.init.ModBlocks;
import noobanidus.mods.lootrexamplemod.neoforge.init.ModItems;

public class LootrExampleModAPIImpl implements ILootrExampleModAPI {
  @Override
  public Block getExampleChestBlock() {
    return ModBlocks.EXAMPLE_CHEST.get();
  }

  @Override
  public BlockEntityType<ExampleChestBlockEntity> getExampleChestBlockEntity() {
    return ModBlockEntities.EXAMPLE_CHEST.get();
  }

  @Override
  public Item getExampleChestBlockItem() {
    return ModItems.EXAMPLE_CHEST.get();
  }
}

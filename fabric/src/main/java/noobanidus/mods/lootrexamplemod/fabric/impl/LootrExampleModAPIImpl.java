package noobanidus.mods.lootrexamplemod.fabric.impl;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import noobanidus.mods.lootrexamplemod.api.ILootrExampleModAPI;
import noobanidus.mods.lootrexamplemod.common.block.entity.ExampleChestBlockEntity;
import noobanidus.mods.lootrexamplemod.fabric.init.ModBlockEntities;
import noobanidus.mods.lootrexamplemod.fabric.init.ModBlocks;
import noobanidus.mods.lootrexamplemod.fabric.init.ModItems;

public class LootrExampleModAPIImpl implements ILootrExampleModAPI {
  @Override
  public Block getExampleChestBlock() {
    return ModBlocks.CHEST;
  }

  @Override
  public BlockEntityType<ExampleChestBlockEntity> getExampleChestBlockEntity() {
    return ModBlockEntities.EXAMPLE_CHEST;
  }

  @Override
  public Item getExampleChestBlockItem() {
    return ModItems.CHEST;
  }
}

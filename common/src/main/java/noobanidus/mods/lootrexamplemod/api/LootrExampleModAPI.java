package noobanidus.mods.lootrexamplemod.api;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import noobanidus.mods.lootrexamplemod.common.block.entity.ExampleChestBlockEntity;

public class LootrExampleModAPI {
  public static ILootrExampleModAPI INSTANCE = null;
  public static final String MODID = "lootrexamplemod";

  public static ResourceLocation rl(String path) {
    return ResourceLocation.fromNamespaceAndPath(MODID, path);
  }

  public static BlockEntityType<ExampleChestBlockEntity> getExampleChestBlockEntity() {
    return INSTANCE.getExampleChestBlockEntity();
  }

  public static Block getExampleChestBlock() {
    return INSTANCE.getExampleChestBlock();
  }

  public static Item getExampleChestBlockItem() {
    return INSTANCE.getExampleChestBlockItem();
  }


}

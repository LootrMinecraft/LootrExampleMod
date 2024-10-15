package noobanidus.mods.lootrexamplemod.common.integration.lootr;

import net.minecraft.world.level.block.entity.BlockEntityType;
import noobanidus.mods.lootr.common.api.ILootrBlockEntityConverter;
import noobanidus.mods.lootr.common.api.data.blockentity.ILootrBlockEntity;
import noobanidus.mods.lootrexamplemod.api.LootrExampleModAPI;
import noobanidus.mods.lootrexamplemod.common.block.entity.ExampleChestBlockEntity;

public class ExampleBlockEntityConverter implements ILootrBlockEntityConverter<ExampleChestBlockEntity> {
  @Override
  public ILootrBlockEntity apply(ExampleChestBlockEntity blockEntity) {
    return (ILootrBlockEntity) LootrIntegration.wrap(blockEntity);
  }

  @Override
  public BlockEntityType<?> getBlockEntityType() {
    return LootrExampleModAPI.getExampleChestBlockEntity();
  }
}

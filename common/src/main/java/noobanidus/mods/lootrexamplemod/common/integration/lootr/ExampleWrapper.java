package noobanidus.mods.lootrexamplemod.common.integration.lootr;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.loot.LootTable;
import noobanidus.mods.lootr.common.api.advancement.IContainerTrigger;
import noobanidus.mods.lootr.common.api.data.LootrBlockType;
import noobanidus.mods.lootr.common.api.data.blockentity.ILootrBlockEntity;
import noobanidus.mods.lootr.common.api.registry.LootrRegistry;
import noobanidus.mods.lootrexamplemod.common.block.entity.ExampleChestBlockEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.UUID;

public record ExampleWrapper(ExampleChestBlockEntity blockEntity) implements ILootrBlockEntity {
  @Override
  public BlockEntity asBlockEntity() {
    return blockEntity;
  }

  @Override
  public void manuallySetLootTable(ResourceKey<LootTable> table, long seed) {
    blockEntity.setLootTable(table, seed);
  }

  @Override
  public @Nullable Set<UUID> getClientOpeners() {
    return blockEntity.getClientOpeners();
  }

  @Override
  public boolean isClientOpened() {
    return blockEntity.isClientOpened();
  }

  @Override
  public void setClientOpened(boolean opened) {
    blockEntity.setClientOpened(opened);
  }

  @Override
  public void markChanged() {
    blockEntity.setChanged();
  }

  @Override
  public LootrBlockType getInfoBlockType() {
    return LootrBlockType.CHEST;
  }

  @Override
  public @NotNull UUID getInfoUUID() {
    return blockEntity.getInfoId();
  }

  @Override
  public String getInfoKey() {
    return blockEntity.getInfoKey();
  }

  @Override
  public boolean isPhysicallyOpen() {
    return blockEntity.getOpenNess(1f) > 0f;
  }

  @Override
  public @NotNull BlockPos getInfoPos() {
    return blockEntity.getBlockPos();
  }

  @Override
  public @Nullable Component getInfoDisplayName() {
    return blockEntity.getDisplayName();
  }

  @Override
  public @NotNull ResourceKey<Level> getInfoDimension() {
    return blockEntity.getLevel().dimension();
  }

  @Override
  public int getInfoContainerSize() {
    return blockEntity.getContainerSize();
  }

  @Override
  public @Nullable NonNullList<ItemStack> getInfoReferenceInventory() {
    return null;
  }

  @Override
  public boolean isInfoReferenceInventory() {
    return false;
  }

  @Override
  public @Nullable ResourceKey<LootTable> getInfoLootTable() {
    return blockEntity.getLootTable();
  }

  @Override
  public long getInfoLootSeed() {
    return blockEntity.getLootTableSeed();
  }

  @Override
  public @Nullable IContainerTrigger getTrigger() {
    return LootrRegistry.getChestTrigger();
  }
}

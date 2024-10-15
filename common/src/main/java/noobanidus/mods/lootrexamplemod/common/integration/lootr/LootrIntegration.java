package noobanidus.mods.lootrexamplemod.common.integration.lootr;

import com.google.common.collect.Sets;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import noobanidus.mods.lootr.common.api.LootrAPI;
import noobanidus.mods.lootr.common.api.data.ILootrInfo;
import noobanidus.mods.lootr.common.api.data.ILootrInfoProvider;
import noobanidus.mods.lootr.common.api.data.blockentity.ILootrBlockEntity;
import noobanidus.mods.lootr.common.api.data.inventory.ILootrInventory;
import noobanidus.mods.lootr.common.block.LootrChestBlock;
import noobanidus.mods.lootrexamplemod.common.block.ExampleChestBlock;
import noobanidus.mods.lootrexamplemod.common.block.entity.ExampleChestBlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.UUID;

public class LootrIntegration {
  public static void saveUUID(ExampleChestBlockEntity blockEntity, CompoundTag tag) {
    if (LootrAPI.shouldDiscard()) {
      return;
    }

    tag.putUUID("LootrId", blockEntity.getInfoId());
  }

  public static ListTag getOpeners(ExampleChestBlockEntity blockEntity) {
    // Don't need to check for a loot table here as we automatically check whenever this is called
    ExampleWrapper wrapper = new ExampleWrapper(blockEntity);
    Set<UUID> visualOpeners = wrapper.getVisualOpeners();
    if (visualOpeners != null && !visualOpeners.isEmpty()) {
      ListTag openers = new ListTag();
      for (UUID opener : Sets.intersection(visualOpeners, LootrAPI.getPlayerIds())) {
        openers.add(NbtUtils.createUUID(opener));
      }
      return openers;
    } else {
      return null;
    }
  }

  public static float getExplosionResistance(ExampleChestBlock block, float superResult) {
    // There's *no* way to control this unfortunately.
    return LootrAPI.getExplosionResistance(block, superResult);
  }

  public static float getDestroyProgress (BlockState state, Player player, BlockGetter getter, BlockPos pos, float superResult) {
    if (getter.getBlockEntity(pos) instanceof ExampleChestBlockEntity blockEntity) {
      if (!blockEntity.hasLootTable()) {
        return superResult;
      }
    }
    return LootrAPI.getDestroyProgress(state, player, getter, pos, superResult);
  }

  public static int getAnalogOutputSignal (BlockState state, Level pLevel, BlockPos pPos, int superResult) {
    if (pLevel.getBlockEntity(pPos) instanceof ExampleChestBlockEntity blockEntity) {
      if (!blockEntity.hasLootTable()) {
        return superResult;
      }
    }
    return LootrAPI.getAnalogOutputSignal(state, pLevel, pPos, superResult);
  }

  public static InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, ServerPlayer serverPlayer) {
    // Loot-table existence is handled by the block itself
    if (serverPlayer.isShiftKeyDown()) {
      LootrAPI.handleProviderSneak(ILootrInfoProvider.of(pos, level), serverPlayer);
    } else if (!LootrChestBlock.isChestBlockedAt(level, pos)) {
      LootrAPI.handleProviderOpen(ILootrInfoProvider.of(pos, level), serverPlayer);
    }
    return InteractionResult.SUCCESS;
  }

  @Nullable
  public static Object wrap(ExampleChestBlockEntity blockEntity) {
    // Double safety check
    if (!blockEntity.hasLootTable()) {
      return null;
    }
    return new ExampleWrapper(blockEntity);
  }

  public static void tick(ExampleChestBlockEntity exampleChestBlockEntity, Level level, BlockPos pos, BlockState state) {
    if (!level.isClientSide()) {
      if (exampleChestBlockEntity.hasLootTable()) {
        LootrAPI.handleProviderTick(new ExampleWrapper(exampleChestBlockEntity));
      }
    }
  }

  public static boolean hasPlayerOpened (ExampleChestBlockEntity blockEntity, Player player) {
    return blockEntity.getClientOpeners().contains(player.getUUID()) || blockEntity.isClientOpened();
  }

  public static String getKey (ExampleChestBlockEntity blockEntity) {
    return ILootrInfo.generateInfoKey(blockEntity.getInfoId());
  }

  @Nullable
  public static UUID getInfoId (Container container) {
    if (container instanceof ILootrInventory inventory) {
      return inventory.getInfo().getInfoUUID();
    }

    return null;
  }
}

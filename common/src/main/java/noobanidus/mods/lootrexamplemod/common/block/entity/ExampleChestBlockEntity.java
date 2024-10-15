package noobanidus.mods.lootrexamplemod.common.block.entity;

import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.ChestLidController;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.state.BlockState;
import noobanidus.mods.lootrexamplemod.api.LootrExampleModAPI;
import noobanidus.mods.lootrexamplemod.common.integration.lootr.LootrIntegration;
import noobanidus.mods.lootrexamplemod.common.integration.lootr.LootrLoaded;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.UUID;

/**
 * Like with {@link noobanidus.mods.lootrexamplemod.common.block.ExampleChestBlock}, this class derives from {@link ChestBlockEntity} and deviates from the vanilla implementation to support Lootr, when this block entity has an associated loot table and Lootr is loaded.
 */
public class ExampleChestBlockEntity extends ChestBlockEntity {
  /**
   * This flag is used to prevent the saving of unique information (specifically the @{link #infoId}) to the item form of the block entity.
   */
  private boolean savingToItem = false;

  /**
   * Stores the contents of the chest when not functioning as a Lootr container.
   */
  private NonNullList<ItemStack> items = NonNullList.withSize(27, ItemStack.EMPTY);
  private final ChestLidController chestLidController = new ChestLidController();

  /**
   * We cannot use {@link ChestBlockEntity#openersCounter} as {@link ContainerOpenersCounter#isOwnContainer} only checks for {@link ChestBlockEntity}.
   */
  private final ContainerOpenersCounter openersCounter = new ContainerOpenersCounter() {
    @Override
    protected void onOpen(Level level, BlockPos blockPos, BlockState blockState) {
      ExampleChestBlockEntity.playSound(level, blockPos, blockState, SoundEvents.CHEST_OPEN);
    }

    @Override
    protected void onClose(Level level, BlockPos blockPos, BlockState blockState) {
      ExampleChestBlockEntity.playSound(level, blockPos, blockState, SoundEvents.CHEST_CLOSE);
    }

    @Override
    protected void openerCountChanged(Level level, BlockPos blockPos, BlockState blockState, int i, int j) {
      ExampleChestBlockEntity.this.signalOpenCount(level, blockPos, blockState, i, j);
    }

    @Override
    protected boolean isOwnContainer(Player player) {
      if (player.containerMenu instanceof ChestMenu chestMenu) {
        // As this can never be a double chest, we can safely perform this simple equality check.
        if (chestMenu.getContainer() == ExampleChestBlockEntity.this) {
          return true;
        }
        // Only if the above check fails do we need to check for Lootr compatibility.
        if (LootrLoaded.isLootrLoaded() && ExampleChestBlockEntity.this.hasLootTable()) {
          // getInfoId will always generate an id if one does not exist. LootrIntegration#getInfoId will return null if the block entity is not a Lootr container.
          return ExampleChestBlockEntity.this.getInfoId().equals(LootrIntegration.getInfoId(chestMenu.getContainer()));
        }
      }
      return false;
    }
  };

  /**
   * Lootr-specific compatibility fields.
   */
  private UUID infoId = null;
  /**
   * This boolean is primarily modified by Lootr's packet system and serves as a redundancy for the synchronization of {@link ExampleChestBlockEntity#clientOpeners}.
   */
  private boolean isClientOpened = false;

  /**
   * This set is synchronized from the server whenever an update packet is generated.
   */
  private final Set<UUID> clientOpeners = new ObjectLinkedOpenHashSet<>();

  /**
   * Generating the key regularly is quite expensive, so it is cached here. When block entity ticking for refresh and decay is enabled, this value is accessed frequently.
   */
  private String cachedKey = null;

  public ExampleChestBlockEntity(BlockPos blockPos, BlockState blockState) {
    super(LootrExampleModAPI.getExampleChestBlockEntity(), blockPos, blockState);
  }

  /**
   * @return Potential null if Lootr is not loaded.
   * <p></p>
   * In instances where the value is null, this function shouldn't be called. Likewise, in instances where this block entity has no loot table, this function shouldn't be called.
   */
  @Nullable
  public String getInfoKey() {
    if (!LootrLoaded.isLootrLoaded()) {
      return null;
    }
    if (cachedKey == null) {
      cachedKey = LootrIntegration.getKey(this);
    }
    return cachedKey;
  }

  /**
   * @return Always generates a UUID.
   */
  public UUID getInfoId() {
    if (infoId == null) {
      infoId = UUID.randomUUID();
    }
    return infoId;
  }

  /**
   * @return If the block entity has a loot table.
   * <p></p>
   * Probably the most commonly used method (asides from whether Lootr is loaded) to determine if Lootr functionality should be enabled.
   */
  public boolean hasLootTable() {
    return getLootTable() != null;
  }

  /**
   * @see ExampleChestBlockEntity#isClientOpened
   */
  public boolean isClientOpened() {
    return isClientOpened;
  }

  /**
   * @see ExampleChestBlockEntity#clientOpeners
   */
  public Set<UUID> getClientOpeners() {
    return clientOpeners;
  }

  /**
   * Primarily modified by Lootr's packet system.
   * @param clientOpened If the texture should be considered open or not.
   */
  public void setClientOpened(boolean clientOpened) {
    isClientOpened = clientOpened;
  }

  @Override
  protected NonNullList<ItemStack> getItems() {
    return this.items;
  }

  @Override
  protected void setItems(NonNullList<ItemStack> nonNullList) {
    this.items = nonNullList;
  }

  /**
   * @param i Vanilla default.
   * @param inventory Vanille default.
   * @return If Lootr is loaded and this has a loot table, returns null, although this method is never called in that instance.
   */
  @Override
  protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
    if (LootrLoaded.isLootrLoaded() && hasLootTable()) {
      // This shouldn't be called if Lootr isn't loaded
      return null;
    }

    return super.createMenu(i, inventory);
  }

  @Override
  public int getContainerSize() {
    return 27;
  }

  @Override
  public void loadAdditional(CompoundTag compound, HolderLookup.Provider provider) {
    // By default, wipe the current items.
    this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
    // Vanilla logic
    if (!this.tryLoadLootTable(compound)) {
      ContainerHelper.loadAllItems(compound, this.items, provider);
    }
    // Lootr-specific logic, although we don't need to check for Lootr being loaded here as the incoming compound tag won't have any Lootr-specific data if Lootr isn't loaded.
    if (hasLootTable()) {
      if (compound.hasUUID("LootrId")) {
        this.infoId = compound.getUUID("LootrId");
      }
      if (this.infoId == null) {
        getInfoId();
      }
      clientOpeners.clear();
      if (compound.contains("LootrOpeners")) {
        ListTag list = compound.getList("LootrOpeners", CompoundTag.TAG_INT_ARRAY);
        for (Tag thisTag : list) {
          clientOpeners.add(NbtUtils.loadUUID(thisTag));
        }
      }
    }
  }

  /**
   * Simple wrapper to modify {@link ExampleChestBlockEntity#savingToItem}
   * @param itemstack Vanilla default.
   * @param provider Vanilla default.
   */
  @Override
  public void saveToItem(ItemStack itemstack, HolderLookup.Provider provider) {
    savingToItem = true;
    super.saveToItem(itemstack, provider);
    savingToItem = false;
  }

  @Override
  protected void saveAdditional(CompoundTag compound, HolderLookup.Provider provider) {
    // We always save the items.
    ContainerHelper.saveAllItems(compound, this.items, provider);
    // We always try saving the loot table.
    this.trySaveLootTable(compound);
    if (LootrLoaded.isLootrLoaded() && !savingToItem && hasLootTable()) {
      // Openers are handled in {@link ExampleChestBlockEntity#getUpdateTag}.
      LootrIntegration.saveUUID(this, compound);
    }
  }

  @Override
  public boolean triggerEvent(int pId, int pType) {
    if (pId == 1) {
      this.chestLidController.shouldBeOpen(pType > 0);
      return true;
    } else {
      return super.triggerEvent(pId, pType);
    }
  }

  @Override
  public void startOpen(Player pPlayer) {
    if (!this.remove && !pPlayer.isSpectator()) {
      this.openersCounter.incrementOpeners(pPlayer, this.getLevel(), this.getBlockPos(), this.getBlockState());
    }

  }

  @Override
  public void stopOpen(Player pPlayer) {
    if (!this.remove && !pPlayer.isSpectator()) {
      this.openersCounter.decrementOpeners(pPlayer, this.getLevel(), this.getBlockPos(), this.getBlockState());
    }
  }

  @Override
  public void recheckOpen() {
    if (!this.remove) {
      this.openersCounter.recheckOpeners(this.getLevel(), this.getBlockPos(), this.getBlockState());
    }
  }

  @Override
  public float getOpenNess(float pPartialTicks) {
    return this.chestLidController.getOpenness(pPartialTicks);
  }

  @Override
  @NotNull
  public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
    CompoundTag result = super.getUpdateTag(provider);
    saveAdditional(result, provider);
    if (LootrLoaded.isLootrLoaded() && hasLootTable()) {
      ListTag openers = LootrIntegration.getOpeners(this);
      if (openers != null && !openers.isEmpty()) {
        result.put("LootrOpeners", openers);
      }
    }
    return result;
  }

  @Override
  @Nullable
  public ClientboundBlockEntityDataPacket getUpdatePacket() {
    return ClientboundBlockEntityDataPacket.create(this, BlockEntity::getUpdateTag);
  }

  @Override
  public void unpackLootTable(@Nullable Player player) {
    if (!LootrLoaded.isLootrLoaded()) {
      super.unpackLootTable(player);
    }
  }

  public static void ticker(Level level, BlockPos pos, BlockState state, ExampleChestBlockEntity chest) {
    if (level.isClientSide()) {
      chest.chestLidController.tickLid();
    }

    if (LootrLoaded.isLootrLoaded() && chest.hasLootTable()) {
      LootrIntegration.tick(chest, level, pos, state);
    }
  }

  protected static void playSound(Level pLevel, BlockPos pPos, BlockState pState, SoundEvent pSound) {
    double d0 = (double) pPos.getX() + 0.5D;
    double d1 = (double) pPos.getY() + 0.5D;
    double d2 = (double) pPos.getZ() + 0.5D;
    pLevel.playSound(null, d0, d1, d2, pSound, SoundSource.BLOCKS, 0.5F, pLevel.random.nextFloat() * 0.1F + 0.9F);
  }
}


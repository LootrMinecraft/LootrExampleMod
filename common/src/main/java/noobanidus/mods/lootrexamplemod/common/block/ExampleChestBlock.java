package noobanidus.mods.lootrexamplemod.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import noobanidus.mods.lootrexamplemod.api.LootrExampleModAPI;
import noobanidus.mods.lootrexamplemod.common.block.entity.ExampleChestBlockEntity;
import noobanidus.mods.lootrexamplemod.common.integration.lootr.LootrIntegration;
import noobanidus.mods.lootrexamplemod.common.integration.lootr.LootrLoaded;
import org.jetbrains.annotations.Nullable;


/**
 * The most complex implementation of a Lootr-compatible container block. Extending {@link ChestBlock} makes the block function identically to a Vanilla chest when Lootr is not present.
 * <p></p>
 * With Lootr installed, its configuration is consulted via {@link LootrIntegration} to ensure its behaviour is consistent with other Lootr containers and Lootr configuration, while it is functioning as a loot container.
 * <p></p>
 * The only exception to this rule of is {@link ExampleChestBlock#getDestroyProgress}, which has no way to determine the presence of a loot table.
 * This means that ExampleChestBlocks with loot tables will function as Lootr containers, and those without loot tables will function as Vanilla chests.
 */
public class ExampleChestBlock extends ChestBlock {

  /**
   * @param properties Block properties.
   */
  public ExampleChestBlock(Properties properties) {
    super(properties, LootrExampleModAPI::getExampleChestBlockEntity);
  }

  /**
   * @return Explosion resistance as calculated according to the Lootr configuration. Note: this is the only block-function that cannot determine whether the container has a loot table or not, and will always act as though it does.
   */
  @Override
  public float getExplosionResistance() {
    if (LootrLoaded.isLootrLoaded()) {
      return LootrIntegration.getExplosionResistance(this, super.getExplosionResistance());
    } else {
      return super.getExplosionResistance();
    }
  }

  /**
   * Determines if Lootr is installed and, if so, whether the block entity associated with this block has a loot table. If so, it will attempt to load a Lootr inventory via {@link LootrIntegration#useWithoutItem}.
   *<p></p>
   * Otherwise, it will defer to the Vanilla defaults.
   * @param state Vanilla default.
   * @param level Vanilla default.
   * @param pos Vanilla default.
   * @param player Vanilla default.
   * @param trace Vanilla default.
   * @return  Vanilla default.
   */
  @Override
  public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult trace) {
    if (level.isClientSide() || player.isSpectator() || !(player instanceof ServerPlayer serverPlayer)) {
      return InteractionResult.CONSUME;
    }
    if (level.getBlockEntity(pos) instanceof ExampleChestBlockEntity blockEntity) {
      if (!LootrLoaded.isLootrLoaded() || !blockEntity.hasLootTable()) {
        return super.useWithoutItem(state, level, pos, player, trace);
      } else {
        return LootrIntegration.useWithoutItem(state, level, pos, serverPlayer);
      }
    } else {
      return super.useWithoutItem(state, level, pos, player, trace);
    }
  }

  /**
   * No deviation from the Vanilla default.
   * @param pos Vanilla default.
   * @param state Vanilla default.
   * @return Vanilla default.
   */
  @Override
  public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
    return new ExampleChestBlockEntity(pos, state);
  }

  /**
   * Deviates from Vanilla by only functioning as a single chest.
   * @param stateIn Vanilla default.
   * @param facing Vanilla default.
   * @param facingState Vanilla default.
   * @param worldIn Vanilla default.
   * @param currentPos Vanilla default.
   * @param facingPos Vanilla default.
   * @return Vanilla default.
   */
  @Override
  public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
    if (stateIn.getValue(WATERLOGGED)) {
      worldIn.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
    }
    return stateIn;
  }

  /**
   * Deviates from Vanilla by simply returning the single chest shape.
   * @param state Vanilla default.
   * @param worldIn Vanilla default.
   * @param pos Vanilla default.
   * @param context Vanilla default.
   * @return Vanilla default.
   */
  @Override
  public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
    return AABB;
  }

  /**
   * Deviates from Vanilla by ensuring that only single chests are placed, and ignoring any nearby other chests.
   * @param context Vanilla default.
   * @return Vanilla default.
   */
  @Override
  public BlockState getStateForPlacement(BlockPlaceContext context) {
    Direction direction = context.getHorizontalDirection().getOpposite();
    FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
    return this.defaultBlockState().setValue(FACING, direction).setValue(TYPE, ChestType.SINGLE).setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
  }

  /**
   * @param state Vanilla default.
   * @param worldIn Vanilla default.
   * @param pos Vanilla default.
   * @return Null if Lootr is loaded and the block entity has a loot table, otherwise it returns the Vanilla default (the block entity itself).
   */
  @Override
  @Nullable
  public MenuProvider getMenuProvider(BlockState state, Level worldIn, BlockPos pos) {
    BlockEntity blockEntity = worldIn.getBlockEntity(pos);
    if (!(blockEntity instanceof ExampleChestBlockEntity example)) {
      return null;
    } else if (!LootrLoaded.isLootrLoaded() || !example.hasLootTable()) {
      return example;
    } else {
      return null;
    }
  }

  /**
   * @param state Vanilla default.
   * @param player Vanilla default.
   * @param level Vanilla default.
   * @param pos Vanilla default.
   * @return If Lootr is loaded, it will return the progress as determined by {@link LootrIntegration#getDestroyProgress}, otherwise it will return the Vanilla default.
   *
   * Note: It cannot determine whether the block entity has a loot table, and will always act as though it does.
   */
  @Override
  public float getDestroyProgress(BlockState state, Player player, BlockGetter level, BlockPos pos) {
    if (LootrLoaded.isLootrLoaded()) {
      return LootrIntegration.getDestroyProgress(state, player, level, pos, super.getDestroyProgress(state, player, level, pos));
    } else {
      return super.getDestroyProgress(state, player, level, pos);
    }
  }

  /**
   * @param pBlockState Vanilla default.
   * @param pLevel Vanilla default.
   * @param pPos Vanilla default.
   * @return If Lootr is loaded, it will return the signal as determined by {@link LootrIntegration#getAnalogOutputSignal}, otherwise it will return the Vanilla default.
   * <p></p>
   * {@link LootrIntegration#getAnalogOutputSignal} will return the signal as determined by the Lootr configuration, or the Vanilla default if the block entity has no loot table.
   */
  @Override
  public int getAnalogOutputSignal(BlockState pBlockState, Level pLevel, BlockPos pPos) {
    if (LootrLoaded.isLootrLoaded()) {
      return LootrIntegration.getAnalogOutputSignal(pBlockState, pLevel, pPos, super.getAnalogOutputSignal(pBlockState, pLevel, pPos));
    } else {
      return super.getAnalogOutputSignal(pBlockState, pLevel, pPos);
    }
  }

  /**
   * @param pLevel Vanilla default.
   * @param pState Vanilla default.
   * @param pBlockEntityType Vanilla default.
   * @param <T> Vanilla default.
   * @return Returns {@link ExampleChestBlockEntity#ticker} by default.
   */
  @Override
  @Nullable
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
    return createTicker(pBlockEntityType, LootrExampleModAPI.getExampleChestBlockEntity(), ExampleChestBlockEntity::ticker);
  }

  protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTicker(BlockEntityType<A> blockEntityType, BlockEntityType<E> blockEntityType2, BlockEntityTicker<? super E> blockEntityTicker) {
    return blockEntityType2 == blockEntityType ? (BlockEntityTicker<A>) blockEntityTicker : null;
  }

  /**
   * Differs from Vanilla by chceking for {@link ExampleChestBlockEntity} instead of {@link net.minecraft.world.level.block.entity.ChestBlockEntity}.
   * @param pState Vanilla default.
   * @param pLevel Vanilla default.
   * @param pPos Vanilla default.
   * @param pRandom Vanilla default.
   */
  @Override
  public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
    BlockEntity blockentity = pLevel.getBlockEntity(pPos);
    if (blockentity instanceof ExampleChestBlockEntity chest) {
      chest.recheckOpen();
    }
  }
}

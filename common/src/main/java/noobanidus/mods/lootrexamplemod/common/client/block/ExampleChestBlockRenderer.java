package noobanidus.mods.lootrexamplemod.common.client.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import noobanidus.mods.lootrexamplemod.api.LootrExampleModAPI;
import noobanidus.mods.lootrexamplemod.common.block.entity.ExampleChestBlockEntity;
import noobanidus.mods.lootrexamplemod.common.integration.lootr.LootrIntegration;
import noobanidus.mods.lootrexamplemod.common.integration.lootr.LootrLoaded;

public class ExampleChestBlockRenderer<T extends ExampleChestBlockEntity> extends ChestRenderer<T> {
  public static final Material MATERIAL = new Material(Sheets.CHEST_SHEET, LootrExampleModAPI.rl("lootr_chest"));
  public static final Material MATERIAL2 = new Material(Sheets.CHEST_SHEET, LootrExampleModAPI.rl("lootr_chest_opened"));
  public static final Material MATERIAL3 = new Material(Sheets.CHEST_SHEET, LootrExampleModAPI.rl("chest"));
  private final ModelPart lid;
  private final ModelPart bottom;
  private final ModelPart lock;

  public ExampleChestBlockRenderer(BlockEntityRendererProvider.Context context) {
    super(context);
    ModelPart modelPart = context.bakeLayer(ModelLayers.CHEST);
    this.bottom = modelPart.getChild("bottom");
    this.lid = modelPart.getChild("lid");
    this.lock = modelPart.getChild("lock");
  }

  public void render(T blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
    Level level = blockEntity.getLevel();
    BlockState blockState = level != null ? blockEntity.getBlockState() : Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.SOUTH);
    poseStack.pushPose();
    float f = blockState.getValue(ChestBlock.FACING).toYRot();
    poseStack.translate(0.5D, 0.5D, 0.5D);
    poseStack.mulPose(Axis.YP.rotationDegrees(-f));
    poseStack.translate(-0.5D, -0.5D, -0.5D);

    float g = blockEntity.getOpenNess(partialTick);
    g = 1.0F - g;
    g = 1.0F - g * g * g;
    Material material = getMaterial(blockEntity);
    VertexConsumer vertexConsumer = material.buffer(bufferSource, RenderType::entityCutout);
    this.render(poseStack, vertexConsumer, this.lid, this.lock, this.bottom, g, packedLight, packedOverlay);

    poseStack.popPose();
  }

  private void render(PoseStack poseStack, VertexConsumer consumer, ModelPart lidPart, ModelPart lockPart, ModelPart bottomPart, float lidAngle, int packedLight, int packedOverlay) {
    lidPart.xRot = -(lidAngle * 1.5707964F);
    lockPart.xRot = lidPart.xRot;
    lidPart.render(poseStack, consumer, packedLight, packedOverlay);
    lockPart.render(poseStack, consumer, packedLight, packedOverlay);
    bottomPart.render(poseStack, consumer, packedLight, packedOverlay);
  }

  protected Material getMaterial(T blockEntity) {
    // Check for Lootr being loaded
    // Check for a loot table
    if (LootrLoaded.isLootrLoaded() && blockEntity.hasLootTable()) {
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft == null || minecraft.player == null) {
        return MATERIAL3;
      }
      Player player = minecraft.player;
      // We don't care about Lootr's Vanilla textures
      // We also don't care about trapped blocks
      if (LootrIntegration.hasPlayerOpened(blockEntity, player)) {
        return MATERIAL2;
      } else {
        return MATERIAL;
      }
    }
    return MATERIAL3;
  }
}

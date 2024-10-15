package noobanidus.mods.lootrexamplemod.fabric.client.item;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class ExampleChestItemRenderer extends noobanidus.mods.lootrexamplemod.common.client.item.ExampleChestItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {
  private static ExampleChestItemRenderer INSTANCE = null;

  @Override
  public void render(ItemStack stack, ItemDisplayContext mode, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
    renderByItem(stack, mode, matrices, vertexConsumers, light, overlay);
  }

  public static BuiltinItemRendererRegistry.DynamicItemRenderer getDynamicInstance () {
    if (INSTANCE == null) {
      INSTANCE = new ExampleChestItemRenderer();
    }

    return INSTANCE;
  }
}

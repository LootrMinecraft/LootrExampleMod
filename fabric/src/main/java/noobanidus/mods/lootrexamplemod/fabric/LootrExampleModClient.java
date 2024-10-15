package noobanidus.mods.lootrexamplemod.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import noobanidus.mods.lootrexamplemod.common.client.block.ExampleChestBlockRenderer;
import noobanidus.mods.lootrexamplemod.fabric.client.item.ExampleChestItemRenderer;
import noobanidus.mods.lootrexamplemod.fabric.init.ModBlockEntities;
import noobanidus.mods.lootrexamplemod.fabric.init.ModBlocks;

public class LootrExampleModClient implements ClientModInitializer {
  @Override
  public void onInitializeClient() {
    BlockEntityRenderers.register(ModBlockEntities.EXAMPLE_CHEST, ExampleChestBlockRenderer::new);
    BuiltinItemRendererRegistry.INSTANCE.register(ModBlocks.CHEST, ExampleChestItemRenderer.getDynamicInstance());
  }
}

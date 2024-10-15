package noobanidus.mods.lootrexamplemod.neoforge;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import noobanidus.mods.lootrexamplemod.api.LootrExampleModAPI;
import noobanidus.mods.lootrexamplemod.common.client.block.ExampleChestBlockRenderer;
import noobanidus.mods.lootrexamplemod.common.client.item.ExampleChestItemRenderer;

@EventBusSubscriber(modid = LootrExampleModAPI.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ClientSetup {
  @SuppressWarnings("unchecked")
  @SubscribeEvent
  public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
    event.registerBlockEntityRenderer(LootrExampleModAPI.getExampleChestBlockEntity(), ExampleChestBlockRenderer::new);
  }

  @SubscribeEvent
  public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
    event.registerItem(new IClientItemExtensions() {
      @Override
      public BlockEntityWithoutLevelRenderer getCustomRenderer() {
        return ExampleChestItemRenderer.getInstance();
      }
    }, LootrExampleModAPI.getExampleChestBlockItem());
  }

}

package noobanidus.mods.lootrexamplemod.neoforge.gen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import noobanidus.mods.lootrexamplemod.api.LootrExampleModAPI;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = LootrExampleModAPI.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ExampleModDataGenerators {
  @SubscribeEvent
  public static void gatherData(GatherDataEvent event) {
    DataGenerator generator = event.getGenerator();
    PackOutput output = event.getGenerator().getPackOutput();
    CompletableFuture<HolderLookup.Provider> provider = event.getLookupProvider();
    ExistingFileHelper helper = event.getExistingFileHelper();

    ExampleModBlockTagProvider blocks;
    generator.addProvider(event.includeServer(), blocks = new ExampleModBlockTagProvider(output, provider, helper));
    generator.addProvider(event.includeServer(), new ExampleModItemTagsProvider(output, provider, blocks.contentsGetter(), helper));
    generator.addProvider(event.includeClient(), new ExampleModAtlasGenerator(output, provider, helper));
  }
}

package noobanidus.mods.lootrexamplemod.neoforge.gen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import noobanidus.mods.lootrexamplemod.api.LootrExampleModAPI;
import noobanidus.mods.lootrexamplemod.api.LootrExampleModTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ExampleModBlockTagProvider extends BlockTagsProvider {
  public ExampleModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
    super(output, lookupProvider, LootrExampleModAPI.MODID, existingFileHelper);
  }

  @Override
  protected void addTags(HolderLookup.Provider provider) {
    tag(BlockTags.MINEABLE_WITH_AXE).add(LootrExampleModAPI.getExampleChestBlock());
    tag(BlockTags.GUARDED_BY_PIGLINS).add(LootrExampleModAPI.getExampleChestBlock());
    tag(Tags.Blocks.CHESTS_WOODEN).add(LootrExampleModAPI.getExampleChestBlock());
    tag(LootrExampleModTags.Blocks.CHESTS).add(LootrExampleModAPI.getExampleChestBlock());
    tag(LootrExampleModTags.Blocks.CONVERT_BLACKLIST).add(LootrExampleModAPI.getExampleChestBlock());
    tag(LootrExampleModTags.Blocks.CATS_CAN_BLOCK).add(LootrExampleModAPI.getExampleChestBlock());
  }

  @Override
  public String getName() {
    return "Lootr Example Mod Block Tags";
  }
}

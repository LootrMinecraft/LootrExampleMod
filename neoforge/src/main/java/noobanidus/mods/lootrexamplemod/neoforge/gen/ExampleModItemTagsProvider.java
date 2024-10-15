package noobanidus.mods.lootrexamplemod.neoforge.gen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import noobanidus.mods.lootrexamplemod.api.LootrExampleModAPI;
import noobanidus.mods.lootrexamplemod.api.LootrExampleModTags;

import java.util.concurrent.CompletableFuture;

public class ExampleModItemTagsProvider extends ItemTagsProvider {
  public ExampleModItemTagsProvider(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_, CompletableFuture<TagLookup<Block>> p_275322_, @org.jetbrains.annotations.Nullable ExistingFileHelper existingFileHelper) {
    super(p_275343_, p_275729_, p_275322_, LootrExampleModAPI.MODID, existingFileHelper);
  }

  @Override
  protected void addTags(HolderLookup.Provider provider) {
    tag(LootrExampleModTags.Items.CHESTS).add(LootrExampleModAPI.getExampleChestBlockItem());
  }

  @Override
  public String getName() {
    return "Lootr Example Mod Item Tags";
  }
}

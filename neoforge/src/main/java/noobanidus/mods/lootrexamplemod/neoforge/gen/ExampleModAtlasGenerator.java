package noobanidus.mods.lootrexamplemod.neoforge.gen;

import net.minecraft.client.renderer.texture.atlas.sources.SingleFile;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SpriteSourceProvider;
import noobanidus.mods.lootr.neoforge.client.block.LootrChestBlockRenderer;
import noobanidus.mods.lootrexamplemod.api.LootrExampleModAPI;
import noobanidus.mods.lootrexamplemod.common.client.block.ExampleChestBlockRenderer;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class ExampleModAtlasGenerator extends SpriteSourceProvider {
  public ExampleModAtlasGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper fileHelper) {
    super(output, lookupProvider, LootrExampleModAPI.MODID, fileHelper);
  }

  @Override
  protected void gather() {
    this.atlas(CHESTS_ATLAS).addSource(new SingleFile(ExampleChestBlockRenderer.MATERIAL.texture(), Optional.empty()));
    this.atlas(CHESTS_ATLAS).addSource(new SingleFile(ExampleChestBlockRenderer.MATERIAL2.texture(), Optional.empty()));
    this.atlas(CHESTS_ATLAS).addSource(new SingleFile(ExampleChestBlockRenderer.MATERIAL3.texture(), Optional.empty()));
  }
}

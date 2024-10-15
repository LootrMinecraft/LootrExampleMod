package noobanidus.mods.lootrexamplemod.api;


import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

/**
 * These tags shadow the standard Lootr tags to allow for data generation without needing to depend on Lootr during the run-time environment.
 */
public class LootrExampleModTags {
  public static class Blocks extends LootrExampleModTags {

    /**
     * Standard chests tag. By including ExampleChestBlock in this tag, it is automatically included in the `lootr:containers` tag.
     * The `lootr:containers` tag is used to determine whether:
     *  - a block is already a Lootr container and thus blacklisted from conversion and
     *  - block-break rules should be applied
     */
    public static final TagKey<Block> CHESTS = tag("chests");

    /**
     * This tag is used by MixinCatSitOnBlockGoal to determine if the block is valid for cats to sit upon.
     */
    public static final TagKey<Block> CATS_CAN_BLOCK = tag("cats_can_block");

    /**
     * This tag is used to determine which blocks should be blacklisted. By default, the inclusion of ExampleChestBlock in `lootr:chests` automatically means that it is blacklisted from conversion, however, for consistency, they are also included in the conversion blacklist.
     */
    public static final TagKey<Block> CONVERT_BLACKLIST = tag("convert/blacklist");

    private static TagKey<Block> tag(String name) {
      return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("lootr", name));
    }
  }

  public static class Items extends LootrExampleModTags {
    /**
     * Item tag version of {@link noobanidus.mods.lootr.common.api.LootrTags.Blocks#CHESTS}
     */
    public static TagKey<Item> CHESTS = tag("chests");

    private static TagKey<Item> tag(String name) {
      return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("lootr", name));
    }
  }
}


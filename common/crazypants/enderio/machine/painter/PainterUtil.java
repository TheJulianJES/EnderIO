package crazypants.enderio.machine.painter;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.util.StringTranslate;

public final class PainterUtil {

  private PainterUtil() {
  }

  public static boolean isMetadataEquivelent(ItemStack one, ItemStack two) {
    if (one == null || two == null) {
      return false;
    }
    return PainterUtil.getSourceBlockId(one) == PainterUtil.getSourceBlockId(two)
        && PainterUtil.getSourceBlockMetadata(one) == PainterUtil.getSourceBlockMetadata(two);
  }

  public static Block getSourceBlock(ItemStack item) {
    NBTTagCompound tag = item.getTagCompound();
    if (tag != null) {
      int blockId = tag.getInteger(BlockPainter.KEY_SOURCE_BLOCK_ID);
      if (blockId >= 0 && blockId < Block.blocksList.length) {
        return Block.blocksList[blockId];
      }
    }
    return null;
  }

  public static int getSourceBlockId(ItemStack item) {
    NBTTagCompound tag = item.getTagCompound();
    if (tag != null) {
      int blockId = tag.getInteger(BlockPainter.KEY_SOURCE_BLOCK_ID);
      if (blockId >= 0 && blockId < Block.blocksList.length) {
        return blockId;
      }
    }
    return -1;
  }

  public static int getSourceBlockMetadata(ItemStack item) {
    NBTTagCompound tag = item.getTagCompound();
    if (tag != null) {
      return tag.getInteger(BlockPainter.KEY_SOURCE_BLOCK_META);
    }
    return 0;
  }

  public static void setSourceBlock(ItemStack item, int sourceId, int meta) {
    NBTTagCompound tag = item.getTagCompound();
    if (tag == null) {
      tag = new NBTTagCompound();
      item.setTagCompound(tag);
    }
    tag.setInteger(BlockPainter.KEY_SOURCE_BLOCK_ID, sourceId);
    tag.setInteger(BlockPainter.KEY_SOURCE_BLOCK_META, meta);

    String sourceName = "";
    if (sourceId > 0) {
      Item i = Item.itemsList[sourceId];
      if (i != null) {
        sourceName = i.getUnlocalizedName(new ItemStack(sourceId, 1, meta));
        sourceName = StatCollector.translateToLocal(sourceName + ".name");
      }
    }
    String typeName;
    typeName = Item.itemsList[item.itemID].getUnlocalizedName(item);
    typeName = StatCollector.translateToLocal(typeName + ".name");

    NBTTagCompound displayTags = new NBTTagCompound();
    displayTags.setString("Name", "Painted " + sourceName + " " + typeName);
    tag.setCompoundTag("display", displayTags);

  }

}

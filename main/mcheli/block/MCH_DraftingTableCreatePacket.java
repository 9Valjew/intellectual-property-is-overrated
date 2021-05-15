package mcheli.block;

import net.minecraft.item.*;
import com.google.common.io.*;
import java.io.*;
import java.util.*;
import net.minecraft.item.crafting.*;
import mcheli.*;
import mcheli.wrapper.*;

public class MCH_DraftingTableCreatePacket extends MCH_Packet
{
    public Item outputItem;
    public Map<Item, Integer> map;
    
    public MCH_DraftingTableCreatePacket() {
        this.map = new HashMap<Item, Integer>();
    }
    
    @Override
    public int getMessageID() {
        return 537395216;
    }
    
    @Override
    public void readData(final ByteArrayDataInput data) {
        try {
            this.outputItem = W_Item.getItemByName(data.readUTF());
            for (int size = data.readByte(), i = 0; i < size; ++i) {
                final String s = data.readUTF();
                final int num = data.readByte();
                final Item item = W_Item.getItemByName(s);
                if (item != null) {
                    this.map.put(item, 0 + num);
                }
            }
        }
        catch (Exception ex) {}
    }
    
    @Override
    public void writeData(final DataOutputStream dos) {
        try {
            dos.writeUTF(this.getItemName(this.outputItem));
            dos.writeByte(this.map.size());
            for (final Item key : this.map.keySet()) {
                dos.writeUTF(this.getItemName(key));
                dos.writeByte((byte)(Object)this.map.get(key));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private String getItemName(final Item item) {
        return W_Item.getNameForItem(item);
    }
    
    public static void send(final IRecipe recipe) {
        if (recipe != null) {
            final MCH_DraftingTableCreatePacket s = new MCH_DraftingTableCreatePacket();
            s.outputItem = ((recipe.func_77571_b() != null) ? recipe.func_77571_b().func_77973_b() : null);
            if (s.outputItem != null) {
                s.map = MCH_Lib.getItemMapFromRecipe(recipe);
                W_Network.sendToServer(s);
            }
            MCH_Lib.DbgLog(true, "MCH_DraftingTableCreatePacket.send outputItem = " + s.outputItem, new Object[0]);
        }
    }
}

package kub3s.eclipse.Item.Backpack;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BackpackInventory {

    public static final int SIZE = 9;
    private final List<ItemStack> items;
    public static final Codec<BackpackInventory> CODEC = RecordCodecBuilder.create((instance) ->
            instance.group(ItemStack.OPTIONAL_CODEC.listOf().fieldOf("items").forGetter(BackpackInventory::getItems))
                    .apply(instance, BackpackInventory::new));

    public BackpackInventory() {
        this.items = new ArrayList();

        for(int i = 0; i < 9; ++i) {
            this.items.add(ItemStack.EMPTY);
        }

    }

    public BackpackInventory(List<ItemStack> items) {
        this.items = new ArrayList(items);

        while(this.items.size() < 9) {
            this.items.add(ItemStack.EMPTY);
        }

    }

    public ItemStack get(int slot) {
        return (ItemStack)this.items.get(slot);
    }

    public void set(int slot, ItemStack stack) {
        this.items.set(slot, stack);
    }

    public List<ItemStack> getItems() {
        return this.items;
    }
}

package kub3s.eclipse.Accsessory.System;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class AccessoryInventory {

    public static final int DEFAULT_SIZE = 9;
    public static final Codec<AccessoryInventory> CODEC = RecordCodecBuilder.create((instance) ->
            instance.group(ItemStack.OPTIONAL_CODEC.listOf().fieldOf("items").forGetter(AccessoryInventory::getSavedItems))
                    .apply(instance, AccessoryInventory::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, AccessoryInventory> STREAM_CODEC;
    private final List<ItemStack> items;

    public AccessoryInventory() {
        this(9);
    }

    public AccessoryInventory(int size) {
        this.items = new ArrayList();

        for(int i = 0; i < size; ++i) {
            this.items.add(ItemStack.EMPTY);
        }

    }

    public AccessoryInventory(List<ItemStack> items) {
        this.items = new ArrayList(items);

        while(this.items.size() < 9) {
            this.items.add(ItemStack.EMPTY);
        }

    }

    public List<ItemStack> getSavedItems() {
        return this.items.stream().map(ItemStack::copy).toList();
    }

    public List<ItemStack> getItems() {
        return this.items;
    }

    public ItemStack get(int slot) {
        return (ItemStack)this.items.get(slot);
    }

    public void set(int slot, ItemStack stack) {
        if (stack != null && !stack.isEmpty()) {
            this.items.set(slot, stack.copy());
        } else {
            this.items.set(slot, ItemStack.EMPTY);
        }

    }

    public int size() {
        return this.items.size();
    }

    public void addSlot() {
        this.items.add(ItemStack.EMPTY);
    }

    public void removeSlot(int slot) {
        this.items.remove(slot);
    }

    static {
        STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.collection(ArrayList::new, ItemStack.OPTIONAL_STREAM_CODEC),
                AccessoryInventory::getSavedItems, AccessoryInventory::new);
    }
}

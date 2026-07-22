package kub3s.eclipse.Item.Backpack;

import kub3s.eclipse.Eclipse;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.resources.Identifier;

public class BackpackAttachment {

    public static final AttachmentType<BackpackInventory> BACKPACK = AttachmentRegistry.create(Identifier
            .fromNamespaceAndPath(Eclipse.MOD_ID, "backpack"), (builder)->
            builder.persistent(BackpackInventory.CODEC).initializer(BackpackInventory::new).copyOnDeath());

    public BackpackAttachment() {
    }

    public static void register() {
    }
}

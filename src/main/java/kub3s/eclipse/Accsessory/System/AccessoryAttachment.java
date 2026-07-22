package kub3s.eclipse.Accsessory.System;

import kub3s.eclipse.Eclipse;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.resources.Identifier;

public class AccessoryAttachment {

    public static final AttachmentType<AccessoryInventory> INVENTORY = AttachmentRegistry
            .create(Identifier.fromNamespaceAndPath(Eclipse.MOD_ID, "accessory_inventory"), (builder)
                    -> builder.persistent(AccessoryInventory.CODEC).initializer(AccessoryInventory::new)
                    .syncWith(AccessoryInventory.STREAM_CODEC, AttachmentSyncPredicate.targetOnly()).copyOnDeath());

    public AccessoryAttachment() {
    }

    public static void register() {
    }
}

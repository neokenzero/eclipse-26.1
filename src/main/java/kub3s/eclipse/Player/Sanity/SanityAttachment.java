package kub3s.eclipse.Player.Sanity;

import java.util.function.Supplier;

import kub3s.eclipse.Eclipse;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.resources.Identifier;

public class SanityAttachment {
    public static final AttachmentType<SanityData> SANITY;

    static {
        SANITY = AttachmentRegistry.create(
                Identifier.fromNamespaceAndPath(Eclipse.MOD_ID, "sanity"), builder -> builder.persistent(SanityData.CODEC).initializer(SanityData::new).syncWith(SanityData.STREAM_CODEC, AttachmentSyncPredicate.targetOnly()).copyOnDeath());
    }

    public static void register() {}
}

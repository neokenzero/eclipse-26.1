//
//package kub3s.eclipse.Accsessory.Item;
//
//import com.mojang.serialization.Codec;
//import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
//import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
//import net.minecraft.resources.Identifier;
//
//public class RegenStoneAttachment {
//
//    public static final AttachmentType<Long> LAST_HIT_TIME =
//            AttachmentRegistry.create(
//                    Identifier.fromNamespaceAndPath(
//                            "eclipse",
//                            "regen_stone_last_hit"
//                    ),
//                    builder -> builder
//                            .persistent(Codec.LONG)
//                            .initializer(() -> 0L)
//                            .copyOnDeath()
//            );
//
//    public static void register() {
//
//    }
//
//}
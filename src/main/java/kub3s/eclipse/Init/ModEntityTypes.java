package kub3s.eclipse.Init;

import kub3s.eclipse.Eclipse;
import kub3s.eclipse.Entity.RatEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ModEntityTypes {

    public static final EntityType<RatEntity> RAT = register(
            "rat",
            EntityType.Builder.<RatEntity>of(RatEntity::new, MobCategory.MISC)
                    .sized(0.35f, 0.32f)
    );

    private static <T extends Entity> EntityType<T> register(String name, EntityType.Builder<T> builder) {
        ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(Eclipse.MOD_ID, name));
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, key, builder.build(key));
    }

    public static void registerAttributes() {
        FabricDefaultAttributeRegistry.register(RAT, RatEntity.createAttributes());
    }

    public static void register() {
        Eclipse.LOGGER.info("Registering EntityTypes for " + Eclipse.MOD_ID);

        registerAttributes();
    }
}

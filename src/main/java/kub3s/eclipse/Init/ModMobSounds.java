package kub3s.eclipse.Init;

import kub3s.eclipse.Eclipse;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

public class ModMobSounds {
    public static final SoundEvent RAT_AMBIENT = registerSoundEvent("entity.rat.ambient");
    public static final SoundEvent RAT_HURT = registerSoundEvent("entity.rat.hurt");
    public static final SoundEvent RAT_ATTACK = registerSoundEvent("entity.rat.attack");

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = Identifier.fromNamespaceAndPath(Eclipse.MOD_ID, name);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
    }

    public static void registerSounds() {
    }
}

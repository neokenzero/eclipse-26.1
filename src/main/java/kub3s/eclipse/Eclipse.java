package kub3s.eclipse;

import kub3s.eclipse.Init.MobSounds;
import kub3s.eclipse.Init.ModEntityTypes;
import kub3s.eclipse.Init.ModItems;
import net.fabricmc.api.ModInitializer;

import net.minecraft.resources.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Eclipse implements ModInitializer {
	public static final String MOD_ID = "eclipse";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModEntityTypes.register();
		MobSounds.registerSounds();
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}

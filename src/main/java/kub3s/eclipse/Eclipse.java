package kub3s.eclipse;

import kub3s.eclipse.Accsessory.Item.AccessoryItem;
import kub3s.eclipse.Accsessory.System.AccessoryAttachment;
import kub3s.eclipse.Accsessory.System.AccessoryManager;
import kub3s.eclipse.Init.*;
import kub3s.eclipse.Network.ModPackets;
import kub3s.eclipse.Network.ModPayloads;
import kub3s.eclipse.Player.Sanity.SanityAttachment;
import kub3s.eclipse.Player.Sanity.SanityData;
import kub3s.eclipse.Player.Sanity.SanityManager;
import kub3s.eclipse.Player.Sanity.SanityPacket;
import net.fabricmc.api.ModInitializer;

import net.minecraft.resources.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Eclipse implements ModInitializer {
	public static final String MOD_ID = "eclipse";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModCommands.register();

		ModItems.registerModItems();

		ModEntityTypes.register();
		ModEntitySpawns.register();
		ModBiomeModifiers.register();
		ModMobSounds.registerSounds();

		ModEffects.register();

		ModMenuTypes.register();
		ModPayloads.register();
		ModPackets.register();

		SanityAttachment.register();

		AccessoryManager.register();
		AccessoryAttachment.register();
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}

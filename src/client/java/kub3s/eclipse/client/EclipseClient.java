package kub3s.eclipse.client;

import kub3s.eclipse.client.Entity.ModModelLayers;
import net.fabricmc.api.ClientModInitializer;

public class EclipseClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		ModModelLayers.registerModelLayers();
	}
}
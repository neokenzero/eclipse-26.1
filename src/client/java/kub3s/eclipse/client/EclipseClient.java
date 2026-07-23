package kub3s.eclipse.client;

import kub3s.eclipse.Eclipse;
import kub3s.eclipse.client.Entity.ModModelLayers;
import kub3s.eclipse.client.HUD.SanityHUD;
import kub3s.eclipse.client.KeyBind.AccessoryKeybind;
import kub3s.eclipse.client.KeyBind.MirrorKeybinds;
import kub3s.eclipse.client.Screens.ModScreens;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.resources.Identifier;

public class EclipseClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		ModModelLayers.registerModelLayers();

		MirrorKeybinds.register();
		AccessoryKeybind.register();
		ModScreens.register();
		//HudElementRegistry.attachElementAfter(VanillaHudElements.CROSSHAIR, Identifier.fromNamespaceAndPath("eclipse", "dia_aim"), (guiGraphics, deltaTracker) -> DiaAimHud.render(guiGraphics));

		HudElementRegistry.attachElementAfter(VanillaHudElements.FOOD_BAR,
				Identifier.fromNamespaceAndPath(Eclipse.MOD_ID, "sanity"),
				(guiGraphics, tickCounter) -> SanityHUD.render(guiGraphics));

	}
}
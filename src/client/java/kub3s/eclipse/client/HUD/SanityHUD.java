package kub3s.eclipse.client.HUD;

import kub3s.eclipse.Eclipse;
import kub3s.eclipse.Player.Sanity.SanityAttachment;
import kub3s.eclipse.Player.Sanity.SanityData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;

public class SanityHUD {
    private static final Identifier ICONS =
            Identifier.fromNamespaceAndPath(
                    "eclipse",
                    "textures/gui/sanity_icons.png"
            );

    private static final int FRAME_SIZE = 16;

    private static final int TEXTURE_W = 48;
    private static final int TEXTURE_H = 16;

    private static final int DRAW_SIZE = 9;

    private static final int SPACING = 8;

    private static final int MAX_ICONS = 10;

    private static final int U_FULL  = 0;
    private static final int U_HALF  = 16;
    private static final int U_EMPTY = 32;


    public static void render(GuiGraphicsExtractor guiGraphics) {
        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.player == null)
            return;

        SanityData sanityData =
                minecraft.player.getAttached(
                        SanityAttachment.SANITY
                );


        if (sanityData == null)
            return;


        int sanity = sanityData.getSanity();

        int screenWidth =
                minecraft.getWindow()
                        .getGuiScaledWidth();

        int screenHeight =
                minecraft.getWindow()
                        .getGuiScaledHeight();

        int left = screenWidth / 2 + 10;
        int top = screenHeight - 49;

        int fullIcons = sanity / 10;

        boolean hasHalf =
                sanity % 10 >= 5;

        for (int i = 0; i < MAX_ICONS; i++) {

            int x = left + i * SPACING;

            int u;

            if (i < fullIcons) {
                u = U_FULL;
            } else if (i == fullIcons && hasHalf) {
                u = U_HALF;
            } else {
                u = U_EMPTY;
            }

            guiGraphics.blit(
                    RenderPipelines.GUI_TEXTURED,
                    ICONS,
                    x,
                    top,
                    u,
                    0,
                    DRAW_SIZE,
                    DRAW_SIZE,
                    FRAME_SIZE,
                    FRAME_SIZE,
                    TEXTURE_W,
                    TEXTURE_H
            );
        }
    }
}

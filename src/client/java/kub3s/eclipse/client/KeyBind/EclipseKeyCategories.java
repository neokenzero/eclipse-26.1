package kub3s.eclipse.client.KeyBind;

import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;

public class EclipseKeyCategories {

    public static final KeyMapping.Category CATEGORY =
            KeyMapping.Category.register(
                    Identifier.fromNamespaceAndPath(
                            "eclipse",
                            "category_eclipse"
                    )
            );
}
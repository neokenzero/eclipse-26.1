package kub3s.eclipse.Item.Backpack;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class BackpackItem extends Item {

    public BackpackItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide()) {

            BackpackInventory inv = player.getAttachedOrCreate(BackpackAttachment.BACKPACK);

            player.openMenu(new SimpleMenuProvider((id, inventory, p) -> new BackpackMenu(id, inventory, inv),
                    Component.translatable("gui.eclipse.backpack")));
        }

        return InteractionResult.SUCCESS;
    }

}

package kub3s.eclipse.Item.Blood;

import kub3s.eclipse.Player.Sanity.SanityAttachment;
import kub3s.eclipse.Player.Sanity.SanityData;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

public class HotBlood extends Item {
    private static final int SANITY_LOSS = 5;

    public HotBlood(Item.Properties properties) {
        super(properties);
    }

    public ItemUseAnimation getUseAnimation(ItemStack stack) {
        return ItemUseAnimation.DRINK;
    }

    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 32;
    }

    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        player.startUsingItem(hand);
        return (InteractionResult)InteractionResult.CONSUME;
    }

    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!level.isClientSide() && entity instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer)entity;
            SanityData sanity = (SanityData)player.getAttachedOrCreate(SanityAttachment.SANITY);
            player.setAttached(SanityAttachment.SANITY, sanity

                    .remove(5));
            player.level().playSound(null, player

                    .getX(), player.getY(), player.getZ(), (Holder) SoundEvents.GENERIC_DRINK, SoundSource.PLAYERS, 1.0F, 1.0F);
        }
        if (entity instanceof Player) {
            Player player = (Player)entity;
            if (!(player.getAbilities()).instabuild) {
                stack.shrink(1);
                if (stack.isEmpty())
                    return new ItemStack((ItemLike)Items.GLASS_BOTTLE);
                if (!player.getInventory().add(new ItemStack((ItemLike)Items.GLASS_BOTTLE)))
                    player.drop(new ItemStack((ItemLike) Items.GLASS_BOTTLE), false);
            }
        }
        return stack;
    }
}
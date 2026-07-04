package kub3s.eclipse.Entity.Goals;

import kub3s.eclipse.Entity.RatEntity;
import kub3s.eclipse.Init.ModEntityTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.PotionItem;

public class HitAndRunGoal extends Goal {

    private final RatEntity mob;
    private LivingEntity target;

    private int state = 0;
    private int runTime = 0;

    public HitAndRunGoal(RatEntity mob) {
        this.mob = mob;
    }

    @Override
    public boolean canUse() {
        target = mob.getTarget();
        return target != null && target.isAlive();
    }

    @Override
    public void tick() {

        if (target == null) return;

        switch (state) {

            case 0 -> {
                mob.getNavigation().moveTo(target, 1.2);

                if (mob.distanceToSqr(target) < 4) {
                    state = 1;
                }
            }

            case 1 -> {
                if (mob instanceof RatEntity rat) {
                    mob.swing(InteractionHand.MAIN_HAND);
                    mob.doHurtTarget((ServerLevel) mob.level(), target);

                    rat.playAttackSound();

                    if (mob.level() instanceof ServerLevel) {
                        runTime = 100;
                    }

                    if (target instanceof Player target) {
                        if (Math.random() <= 0.05) {
                            target.addEffect(new MobEffectInstance(MobEffects.POISON, 200, 0));
                        }

                        var inventory = target.getInventory();

                        for (int i = 0; i < inventory.getContainerSize(); i++) {

                            var item = inventory.getItem(i);

                            if (!item.isEmpty()
                                    && item.has(DataComponents.FOOD)
                                    && !(item.getItem() instanceof PotionItem)) {

                                var stolen = item.copyWithCount(1);

                                mob.setItemInHand(InteractionHand.MAIN_HAND, stolen);
                                mob.setDropChance(EquipmentSlot.MAINHAND, 1.0F);

                                item.shrink(1);

                                target.sendOverlayMessage(
                                        Component.translatable("chat.eclipse.rat.food_stolen")
                                                .withStyle(ChatFormatting.RED)
                                );

                                break;
                            }
                        }
                    }
                    state = 2;
                    runTime = 40;
                }
            }

            case 2 -> {
                runTime--;

                var escape = DefaultRandomPos.getPosAway(mob, 10, 5, target.position());

                if (escape != null) {
                    mob.getNavigation().moveTo(escape.x, escape.y, escape.z, 1.4);
                }

                if (runTime <= 0) {
                    state = 0;
                }
            }
        }
    }
}
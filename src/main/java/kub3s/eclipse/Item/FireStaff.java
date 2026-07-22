package kub3s.eclipse.Item;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.hurtingprojectile.SmallFireball;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class FireStaff extends Item {

    private static final int COOLDOWN_TICKS = 60;

    public FireStaff(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {

        if (player.getCooldowns().isOnCooldown(this.getDefaultInstance())) {
            return InteractionResult.FAIL;
        }

        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        if (!(level instanceof ServerLevel serverLevel)) {
            return InteractionResult.PASS;
        }

        Vec3 look = player.getLookAngle();
        RandomSource random = serverLevel.getRandom();

        for (int i = 0; i < 10; i++) {

            double spread = 0.1;
            double mx = look.x + (random.nextDouble() - 0.5) * spread;
            double my = look.y + (random.nextDouble() - 0.5) * spread;
            double mz = look.z + (random.nextDouble() - 0.5) * spread;

            Vec3 powerVector = new Vec3(mx, my, mz);

            SmallFireball fireball = new SmallFireball(
                    serverLevel,
                    player,
                    powerVector
            );

            fireball.setPos(
                    player.getX() + look.x * 1.5,
                    player.getEyeY() - 0.1,
                    player.getZ() + look.z * 1.5
            );

            serverLevel.addFreshEntity(fireball);
        }


        serverLevel.playSound(
                null,
                player.getX(),
                player.getY(),
                player.getZ(),
                SoundEvents.BLAZE_SHOOT,
                SoundSource.PLAYERS,
                1.0F,
                1.0F
        );

        player.getCooldowns().addCooldown(this.getDefaultInstance(), COOLDOWN_TICKS);

        return InteractionResult.SUCCESS;
    }
}

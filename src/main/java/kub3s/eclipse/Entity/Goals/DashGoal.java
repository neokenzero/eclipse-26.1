package kub3s.eclipse.Entity.Goals;

import kub3s.eclipse.Entity.WerewolfEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;
import java.util.EnumSet;

public class DashGoal extends Goal {

    private final WerewolfEntity mob;
    private LivingEntity target;

    private final double dashRange = 10.0;

    private int cooldownTicks = 0;
    private int dashDuration = 0;

    public DashGoal(WerewolfEntity mob) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (cooldownTicks > 0) {
            cooldownTicks--;
            return false;
        }

        target = mob.getTarget();
        if (target == null || !target.isAlive()) return false;

        return mob.distanceTo(target) >= dashRange;
    }

    @Override
    public void start() {
        this.dashDuration = 15;
    }

    @Override
    public boolean canContinueToUse() {
        return this.dashDuration > 0 && target != null && target.isAlive();
    }

    @Override
    public void stop() {
        this.cooldownTicks = 600;
        this.dashDuration = 0;
    }

    @Override
    public void tick() {
        if (target == null) return;

        mob.getLookControl().setLookAt(target, 180.0F, 180.0F);
        mob.setYRot(mob.yHeadRot);

        Vec3 direction = new Vec3(
                target.getX() - mob.getX(),
                0.1,
                target.getZ() - mob.getZ()
        ).normalize();

        double dashSpeed = 1.2;

        mob.setDeltaMovement(direction.scale(dashSpeed));

        if (this.dashDuration > 0) {
            this.dashDuration--;
        }
    }
}

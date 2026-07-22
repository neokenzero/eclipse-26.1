package kub3s.eclipse.Boss.Ent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import kub3s.eclipse.Init.ModEntityTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.skeleton.Bogged;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.arrow.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class EntEntity extends Monster implements RangedAttackMob {

    private static final int MAX_MINIONS = 4;
    private static final double MASS_ATTACK_RADIUS = (double) 10.0F;
    private static final double MASS_ATTACK_RING_RADIUS = (double) 5.0F;
    private final List<UUID> minions = new ArrayList();
    private ServerBossEvent bossEvent;
    private boolean isPerformingAbility = false;
    private int abilityCheckCooldown = 0;
    private int pendingSlimeDelay = 0;
    private Player pendingSlimeTarget = null;

    public EntEntity(EntityType<? extends Monster> entityType, Level level) {
        super(ModEntityTypes.ENT, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes().add(Attributes.MAX_HEALTH, (double) 300.0F).add(Attributes.ATTACK_DAMAGE, (double) 8.0F).add(Attributes.MOVEMENT_SPEED, (double) 0.25F).add(Attributes.FOLLOW_RANGE, (double) 32.0F).add(Attributes.SCALE, 0.85);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new RangedBowAttackGoal<>(this, (double) 1.0F, 20, 15.0F));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, (double) 1.0F));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public void performRangedAttack(LivingEntity target, float distanceFactor) {
        Arrow arrow = new Arrow(this.level(), this, new ItemStack(Items.ARROW), (ItemStack) null);
        double dx = target.getX() - this.getX();
        double dy = target.getY(0.3333) - arrow.getY();
        double dz = target.getZ() - this.getZ();
        double horizontalDist = Math.sqrt(dx * dx + dz * dz);
        arrow.shoot(dx, dy + horizontalDist * 0.2, dz, 1.6F, (float) (14 - this.level().getDifficulty().getId() * 4));
        this.playSound(SoundEvents.SKELETON_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level().addFreshEntity(arrow);
    }

    public void tick() {
        super.tick();
        if (!this.level().isClientSide()) {
            ServerLevel serverLevel = (ServerLevel) this.level();
            this.updateBossBar(serverLevel);
            this.tickSlimeDelay();
            if (!this.isPerformingAbility) {
                --this.abilityCheckCooldown;
                if (this.abilityCheckCooldown <= 0) {
                    this.abilityCheckCooldown = 80;
                    if (this.random.nextDouble() <= 0.4) {
                        this.performRandomAbility(serverLevel);
                    }
                }
            }

        }
    }

    private void updateBossBar(ServerLevel level) {
        if (this.bossEvent == null) {
            this.bossEvent = new ServerBossEvent(UUID.randomUUID(), Component.literal("Энт").withStyle(ChatFormatting.DARK_GREEN), BossBarColor.GREEN, BossBarOverlay.PROGRESS);
        }

        AttributeInstance maxHealthAttr = this.getAttribute(Attributes.MAX_HEALTH);
        double maxHealth = maxHealthAttr != null ? maxHealthAttr.getBaseValue() : (double) 300.0F;
        this.bossEvent.setProgress((float) ((double) this.getHealth() / maxHealth));

        for (Player player : level.getEntitiesOfClass(Player.class, this.getBoundingBox().inflate((double) 48.0F))) {
            this.bossEvent.addPlayer((ServerPlayer) player);
        }

    }

    public void die(DamageSource cause) {
        super.die(cause);
        if (this.bossEvent != null) {
            this.bossEvent.removeAllPlayers();
        }

    }

    public void setCustomName(Component name) {
        super.setCustomName(name);
        this.setCustomNameVisible(true);
    }

    private void performRandomAbility(ServerLevel level) {
        this.isPerformingAbility = true;
        int roll = this.random.nextInt(4);
        switch (roll) {
            case 0 -> this.massAttack(level);
            case 1 -> this.knockBackAttack(level);
            case 2 -> this.summonMinions(level);
            case 3 -> this.slimeAttack(level);
        }

    }

    private void massAttack(ServerLevel level) {
        Vec3 center = this.position();
        level.playSound((Entity) null, center.x, center.y, center.z, SoundEvents.LEAF_LITTER_BREAK, SoundSource.HOSTILE, 100.0F, 0.1F);
        level.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK_CRUMBLE, Blocks.ROOTED_DIRT.defaultBlockState()), center.x, center.y, center.z, 50, (double) 1.0F, (double) 1.0F, (double) 1.0F, (double) 0.5F);
        int points = 120;
        double y = center.y + 0.1;

        for (int i = 0; i < points; ++i) {
            double angle = (Math.PI * 2D) * (double) i / (double) points;
            double x = center.x + (double) 5.0F * Math.cos(angle);
            double z = center.z + (double) 5.0F * Math.sin(angle);
            level.sendParticles(new DustParticleOptions(65280, 1.0F), x, y, z, 2, (double) 0.5F, (double) 0.5F, (double) 0.5F, (double) 0.0F);
        }

        for (LivingEntity living : level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate((double) 10.0F), (e) -> e.isAlive() && !e.equals(this) && !this.minions.contains(e.getUUID()))) {
            living.setDeltaMovement(living.getDeltaMovement().x, (double) 2.0F, living.getDeltaMovement().z);
            living.hurtServer(level, level.damageSources().mobAttack(this), 10.0F);
            living.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 100, 1, false, true));
            level.playSound((Entity) null, living.getX(), living.getY(), living.getZ(), SoundEvents.LEAF_LITTER_BREAK, SoundSource.HOSTILE, 100.0F, 0.1F);
            level.sendParticles(ParticleTypes.HAPPY_VILLAGER, living.getX(), living.getY(), living.getZ(), 15, (double) 0.5F, (double) 0.5F, (double) 0.5F, (double) 0.0F);
        }

        this.isPerformingAbility = false;
    }

    private void knockBackAttack(ServerLevel level) {
        List<Player> nearby = level.getEntitiesOfClass(Player.class, this.getBoundingBox().inflate((double) 5.0F), LivingEntity::isAlive);
        if (nearby.isEmpty()) {
            this.isPerformingAbility = false;
        } else {
            Player target = (Player) nearby.get(this.random.nextInt(nearby.size()));
            Vec3 knockback = target.position().subtract(this.position()).normalize().scale((double) 1.5F);
            target.setDeltaMovement(knockback.x, (double) 0.5F, knockback.z);
            target.addEffect(new MobEffectInstance(MobEffects.INSTANT_DAMAGE, 1, 0));
            this.drawParticleLine(level, this.position(), target.position());
            this.isPerformingAbility = false;
        }
    }

    private void summonMinions(ServerLevel level) {
        if (this.minions.size() >= 4) {
            this.isPerformingAbility = false;
        } else {
            Vec3 loc = this.position();
            int maxToSpawn = Math.min(2, 4 - this.minions.size());

            for (int i = 0; i < maxToSpawn; ++i) {
                Bogged minion = (Bogged) EntityType.BOGGED.create(level, EntitySpawnReason.MOB_SUMMONED);
                if (minion != null) {
                    double ox = (this.random.nextDouble() - (double) 0.5F) * (double) 4.0F;
                    double oz = (this.random.nextDouble() - (double) 0.5F) * (double) 4.0F;
                    minion.setPos(loc.x + ox, loc.y, loc.z + oz);
                    AttributeInstance maxHealthAttr = minion.getAttribute(Attributes.MAX_HEALTH);
                    if (maxHealthAttr != null) {
                        maxHealthAttr.setBaseValue((double) 80.0F);
                        minion.setHealth(80.0F);
                    }

                    AttributeInstance attackDamageAttr = minion.getAttribute(Attributes.ATTACK_DAMAGE);
                    if (attackDamageAttr != null) {
                        attackDamageAttr.setBaseValue((double) 6.0F);
                    }

                    minion.setPersistenceRequired();
                    minion.setSilent(true);
                    minion.setCanPickUpLoot(false);
                    minion.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.STONE_AXE));
                    minion.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
                    level.addFreshEntity(minion);
                    this.minions.add(minion.getUUID());
                }
            }

            this.isPerformingAbility = false;
        }
    }

    private void slimeAttack(ServerLevel level) {
        List<Player> nearby = level.getEntitiesOfClass(Player.class, this.getBoundingBox().inflate((double) 20.0F), LivingEntity::isAlive);
        if (nearby.isEmpty()) {
            this.isPerformingAbility = false;
        } else {
            Player target = (Player) nearby.stream().min((a, b) -> Float.compare(a.getHealth(), b.getHealth())).orElse((Player) null);

            target.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 200, 2));
            target.sendSystemMessage(Component.literal("Слизь обволакивает вашу голову!").withStyle(ChatFormatting.GREEN));
            this.pendingSlimeDelay = 100;
            this.pendingSlimeTarget = target;
        }
    }

    private void tickSlimeDelay() {
        if (this.pendingSlimeDelay > 0) {
            if (this.pendingSlimeTarget != null && this.pendingSlimeTarget.isAlive() && this.pendingSlimeTarget.isInWater()) {
                this.pendingSlimeTarget.removeEffect(MobEffects.SLOWNESS);
                this.pendingSlimeTarget.sendSystemMessage(Component.literal("Слизь смыло водой!").withStyle(ChatFormatting.AQUA));
                this.pendingSlimeTarget = null;
                this.pendingSlimeDelay = 0;
                this.isPerformingAbility = false;
            } else {
                if (this.pendingSlimeTarget != null && this.pendingSlimeTarget.isAlive()) {
                    ServerLevel level = (ServerLevel) this.level();
                    double headY = this.pendingSlimeTarget.getY() + (double) this.pendingSlimeTarget.getEyeHeight() + 0.15;
                    level.sendParticles(ParticleTypes.ITEM_SLIME, this.pendingSlimeTarget.getX(), headY, this.pendingSlimeTarget.getZ(), 3, 0.18, 0.08, 0.18, 0.01);
                }

                --this.pendingSlimeDelay;
                if (this.pendingSlimeDelay <= 0) {
                    Player target = this.pendingSlimeTarget;
                    this.pendingSlimeTarget = null;
                    if (target != null && target.isAlive()) {
                        target.addEffect(new MobEffectInstance(MobEffects.POISON, 100, 1));
                        target.sendSystemMessage(Component.literal("Слизь впиталась в кожу! Вы отравлены!").withStyle(ChatFormatting.DARK_GREEN));
                        this.isPerformingAbility = false;
                    } else {
                        this.isPerformingAbility = false;
                    }
                }
            }
        }
    }

    private void drawParticleLine(ServerLevel level, Vec3 from, Vec3 to) {
        int steps = 20;

        for (int i = 0; i <= steps; ++i) {
            double t = (double) i / (double) steps;
            Vec3 p = from.lerp(to, t);
            level.sendParticles(ParticleTypes.CRIT, p.x, p.y + (double) 1.0F, p.z, 1, (double) 0.0F, (double) 0.0F, (double) 0.0F, (double) 0.0F);
        }
    }
}
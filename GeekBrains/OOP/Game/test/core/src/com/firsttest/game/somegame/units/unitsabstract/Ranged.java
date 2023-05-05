package com.firsttest.game.somegame.units.unitsabstract;


import com.firsttest.game.somegame.service.Position;
import com.firsttest.game.somegame.units.Army;

public abstract class Ranged extends BaseArmyUnit{

    protected int ammo, maxAmmo;
    protected int range;

    /**
     * тип брони - лёгкая
     * тип урона - острый
     */
    protected Ranged(float hp, int armor, int initiative, Position pos, int[] baseDmg, int critChance, int accuracy, int evasion, int ammo, int range) {
        super(hp, armor, ArmorType.light, initiative, pos, DamageType.sharp, baseDmg, critChance, accuracy, evasion);
        this.ammo = this.maxAmmo = ammo;
        this.range = range;
    }

    @Override
    public String unitInfo() {
        return String.format("%s AMMO: %2d/%-2d RNG: %-2d",super.unitInfo(), this.ammo, this.maxAmmo, this.range);
    }

    @Override
    public String unitStats() {
        return String.format("%s AMMO: %2d/%-2d",super.unitStats(), this.ammo, this.maxAmmo);
    }

    // fixme если ближайший противник на расстоянии удара , то выбор , либо отступить, либо ударить ножом (половина урона)
    @Override
    public void action(Army ally, Army enemy) {
        if (state == State.dead) return;
        if (ammo <= 0) return;
        shoot(findNearestUnit(enemy));
        // ещё проверку на рэндж шутера. если враг дальше - шаг вперёд
    }

    private void shoot(BaseUnit enemy) {
        enemy.takeDamage(calculateDamage(enemy));
        ammo--;
    }

    @Override
    public void recieveHelp () {
        this.ammo++;
    }

    public int getAmmo(){return this.ammo;}
}



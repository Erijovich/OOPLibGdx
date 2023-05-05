package com.firsttest.game.somegame.units.unitsabstract;


import com.firsttest.game.somegame.service.Position;
import com.firsttest.game.somegame.service.SpellBook;
import com.firsttest.game.somegame.units.Army;

public abstract class Mage extends BaseArmyUnit{
    int mp, maxMp;
    int mpRegSpeed;


    protected Mage(float hp, int armor, int initiative, Position pos, int[] baseDmg, int critChance, int accuracy, int evasion, int mp, int mpRegSpeed) {
        super(hp, armor, ArmorType.light, initiative, pos, DamageType.magic, baseDmg, critChance, accuracy, evasion);
        this.mp = this.maxMp = mp;
        this.mpRegSpeed = mpRegSpeed;
    }

    @Override
    public String unitInfo() {
        return  String.format("%s MP: %3d/%-3d REG: %-3d",super.unitInfo(), this.mp, this.maxMp, this.mpRegSpeed);
    }

    @Override
    public String unitStats() {
        return  String.format("%s MP: %3d/%-3d",super.unitStats(), this.mp, this.maxMp);
    }

    /**
     * магический класс три раза кидает "кубик" размера своего текущего количества маны
     *
     */
    // fixme если ближайший противник на расстоянии удара , то выбор , либо отступить, либо ударить посохом // (половина урона)
    // fixme пока спелбук не используется. заклинания только базовые используются/  и нет выбора наиболее раненого союзника, например
    @Override
    public void action(Army ally, Army enemy) {
        if (state == State.dead) return;
        this.mp = Math.min(mp+mpRegSpeed, maxMp);
        int chooseCast = rnd.nextInt(3);
        if (mp>=25) {
            switch (chooseCast) {
                case 0:
                    float leastHp = 1000;
                    BaseUnit unitToHeal = null;
                    for (BaseUnit unit : ally.getArmy()) {
                        if (unit.isAlive()) {
                            if (unit.getHp() < leastHp) {
                                leastHp = Math.min(leastHp, unit.getHp());
                                unitToHeal = unit;
                            }
                        }
                    }
                    unitToHeal.takeDamage(SpellBook.heal10.getPower());
                    mp -= SpellBook.heal50.getCost();
                    break;
                case 1:
                    findNearestUnit(enemy).takeDamage(SpellBook.fire10.getPower());
                    mp -= SpellBook.fire50.getCost();
                    break;
                default:
                    break;
            }
        }
//        castSpell(findNearestUnit(enemy)); // fixme продумать заклинания и на своих и на чужих
    }

//    public void castSpell(BaseUnit unit) {
//
//    }
}

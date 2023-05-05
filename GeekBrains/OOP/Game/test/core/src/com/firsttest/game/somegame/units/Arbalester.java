package com.firsttest.game.somegame.units;


import com.firsttest.game.somegame.service.Position;
import com.firsttest.game.somegame.units.unitsabstract.Ranged;

public class Arbalester extends Ranged {

    private static final int HP, ARMOR, INITIATIVE, CRITCHANCE, ACCURACY, EVASION, AMMO, RANGE;
    private static final int[] BASEDMG;

    static {
        HP = 200;
        ARMOR = 100;
        INITIATIVE = 5;
        CRITCHANCE = 10;
        ACCURACY = 60;
        EVASION = 10;
        AMMO = 10;
        RANGE = 10;
        BASEDMG = new int[]{20,30};
    }

    /**
     * переопределяем тип урона на blunt, арбалетчик эффективнее против тяжёлой брони
     */
    protected Arbalester(Position pos) {
        super(HP, ARMOR, INITIATIVE, pos, BASEDMG, CRITCHANCE, ACCURACY, EVASION, AMMO, RANGE);
        this.armType = ArmorType.medium;
        this.dmgType = DamageType.blunt;
    }

    @Override
    public String getIcon () {
        if (isAlive()) return "\uD83D\uDD2B";
        else return super.getIcon();
    }

    @Override
    public String unitType() {
        return "Arbst";
    }

    @Override
    public void action(Army ally, Army enemy) {
        super.action(ally, enemy);
    }

    /**
     * Поставить болт с урановым наконечником:
     * на один ход увеличение урона (baseDmg) за счёт уменьшения инициативы.
     */
    public void charge() {
        this.baseDmg[0] *= 1.5;
        this.baseDmg[1] *= 1.5;
        this.initiative *= 2;
        this.critChance *= 1.5;
    }

    /** возврат к базовым настройкам */
    public void unCharge() {
        this.baseDmg = BASEDMG;
        this.initiative = INITIATIVE;
        this.critChance = CRITCHANCE;
    }
}

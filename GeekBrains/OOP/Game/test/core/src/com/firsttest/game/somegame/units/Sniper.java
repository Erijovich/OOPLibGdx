package com.firsttest.game.somegame.units;

import com.firsttest.game.somegame.service.Position;
import com.firsttest.game.somegame.units.unitsabstract.Ranged;

public class Sniper extends Ranged {

    private static final int HP, ARMOR, INITIATIVE, CRITCHANCE, ACCURACY, EVASION, AMMO, RANGE;
    private static final int[] BASEDMG;

    static {
        HP = 200;
        ARMOR = 100;
        INITIATIVE = 3;
        CRITCHANCE = 20;
        ACCURACY = 70;
        EVASION = 15;
        AMMO = 20;
        RANGE = 10;
        BASEDMG = new int[]{15,25};
    }

    public Sniper(Position pos) {
        super(HP, ARMOR, INITIATIVE, pos, BASEDMG, CRITCHANCE, ACCURACY, EVASION, AMMO,  RANGE);
    }

    @Override
    public String getIcon () {
        if (isAlive()) return "\uD83C\uDFF9";
        else return super.getIcon();
    }

    @Override
    public String unitType() {
        return "Snipr";
    }


    @Override
    public void action(Army ally, Army enemy) {
        super.action(ally, enemy);
    }

    /**
     * Прицелиться:
     * на один ход увеличение точности (accuracy) за счёт уменьшения инициативы.
     * ток пока не понятно , как реализовывать
     */
    public void aim() {
        this.accuracy *= 1.3;
        this.initiative *= 2;
        this.critChance *= 1.5;
    }

    /** возврат к базовым настройкам */
    public void unAim() {
        this.accuracy = ACCURACY;
        this.initiative = INITIATIVE;
        this.critChance = CRITCHANCE;
    }
}

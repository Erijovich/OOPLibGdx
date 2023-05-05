package com.firsttest.game.somegame.units;

import com.firsttest.game.somegame.service.Position;
import com.firsttest.game.somegame.units.unitsabstract.Melee;

public class Thief extends Melee {

    private static final int HP, AP, INITIATIVE, CRITCHANCE, ACCURACY,EVASION;
    private static final int[] BASEDMG;

    static {
        HP = 200;
        AP = 150;
        INITIATIVE = 1;
        CRITCHANCE = 30;
        ACCURACY = 80;
        EVASION = 30;
        BASEDMG = new int[]{10,50};
    }

    protected Thief(Position pos) {
        super(HP, AP, INITIATIVE, pos, DamageType.sharp, BASEDMG, CRITCHANCE, ACCURACY, EVASION);
    }

    @Override
    public String getIcon () {
        if (isAlive()) return "\uD83E\uDD35";
        else return super.getIcon();
    }

    @Override
    public String unitType() {
        return "Thief";
    }

//    @Override
//    public void action(List<BaseUnit> ally, List<BaseUnit> enemy) {
//        super.action(ally, enemy);
//    }

    /**
     * Режим скрытности:
     * увеличение точности, критического шанса и уклонения за счёт экстремального уменьшения здоровья и брони
     */
    public void hide() {
        this.initiative *= 3;
        this.accuracy *= 2;
        this.critChance *= 1.5;
        this.evasion = 65;
        this.hp *= 0.1;
        this.ap *= 0.2;
    }

    /** возврат к базовым настройкам */
    public void unHide() {
        this.initiative = INITIATIVE;
        this.accuracy = ACCURACY;
        this.critChance = CRITCHANCE;
        this.evasion = EVASION;
        this.hp /= 0.1;
        this.ap /= 0.2;
    }
}

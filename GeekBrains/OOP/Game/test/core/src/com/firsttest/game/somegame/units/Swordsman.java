package com.firsttest.game.somegame.units;

import com.firsttest.game.somegame.service.Position;
import com.firsttest.game.somegame.units.unitsabstract.Melee;

public class Swordsman extends Melee {

    private static final int HP, ARMOR, INITIATIVE, CRITCHANCE, ACCURACY, EVASION;
    private static final int[] BASEDMG;

    static {
        HP = 400;
        ARMOR = 150;
        INITIATIVE = 6;
        CRITCHANCE = 1;
        ACCURACY = 60;
        EVASION = 5;
        BASEDMG = new int[]{30,40};
    }

    protected Swordsman(Position pos){
        super(HP, ARMOR, INITIATIVE, pos, DamageType.sharp, BASEDMG, CRITCHANCE, ACCURACY,EVASION);
    }

    @Override
    public String getIcon () {
        if (isAlive()) return "\uD83E\uDD3A";
        else return super.getIcon();
    }

    @Override
    public String unitType() {
        return "Sword";
    }

//    @Override
//    public void action(List<BaseUnit> ally, List<BaseUnit> enemy) {
//        super.action(ally, enemy);
//    },


    /**
     * Поднять щит:
     * увеличение брони за счёт уменьшения инициативы и точности.
     */

    public void raiseShield() {
        this.initiative *= 1.5;
        this.accuracy *= +.8;
        this.ap += 150;
    }

    /** возврат к базовым настройкам */
    public void lowerShield() {
        this.initiative *= INITIATIVE;
        this.accuracy *= ACCURACY;
        this.ap = Math.max(0, this.ap - 150); // если основную броню уже погрызли
    }

}

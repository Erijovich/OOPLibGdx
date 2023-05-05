package com.firsttest.game.somegame.units;

import com.firsttest.game.somegame.service.Position;
import com.firsttest.game.somegame.units.unitsabstract.Mage;

public class Wizard extends Mage {

    private static final int HP, AP, INITIATIVE, CRITCHANCE, ACCURACY, EVASION, MANA, MANAREG;
    private static final int[] BASEDMG;

    static {
        HP = 150;
        AP = 100;
        INITIATIVE = 3;
        CRITCHANCE = 20;
        ACCURACY = 70;
        EVASION = 3;
        MANA = 100;
        MANAREG = 10;
        BASEDMG = new int[]{5,15};
    }

    protected Wizard(Position pos) {
        super(HP, AP, INITIATIVE, pos, BASEDMG, CRITCHANCE, ACCURACY, EVASION, MANA, MANAREG);
    }

    @Override
    public String getIcon () {
        if (isAlive()) return "🧙";
        else return super.getIcon();
    }

    @Override
    public String unitType() {
        return "Wizrd";
    }
//
//    @Override
//    public void action(List<BaseUnit> ally, List<BaseUnit> enemy) {
//        super.action(ally, enemy);
//    }

    /**
     * варианты: дебафф врага, атака по площади, удвоение маны. А может просто каст заклинания?
     */


}


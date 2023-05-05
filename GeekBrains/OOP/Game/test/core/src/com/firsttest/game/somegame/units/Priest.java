package com.firsttest.game.somegame.units;

import com.firsttest.game.somegame.service.Position;
import com.firsttest.game.somegame.units.unitsabstract.Mage;

public class Priest extends Mage {

    private static final int HP, AP, INITIATIVE, CRITCHANCE, ACCURACY, EVASION, MANA, MANAREG;
    private static final int[] BASEDMG;

    static {
        HP = 150;
        AP = 100;
        INITIATIVE = 3;
        CRITCHANCE = 20;
        ACCURACY = 70;
        EVASION = 20;
        MANA = 100;
        MANAREG = 10;
        BASEDMG = new int[]{10,10};
    }

    protected Priest(Position pos) {
        super(HP, AP, INITIATIVE, pos, BASEDMG, CRITCHANCE, ACCURACY,EVASION, MANA, MANAREG);
        this.armType = ArmorType.naked;
    }

    @Override
    public String getIcon () {
        if (isAlive()) return "\uD83D\uDC69\uD83C\uDFFC\u200D⚕️";
        else return super.getIcon();
    }

    @Override
    public String unitType() {
        return "Prist";
    }

    @Override
    public void action(Army ally, Army enemy) {
        super.action(ally, enemy);
    }

    /**
     * Варианты: оживить, каст заклинания, бафф союзника (но как это отслеживать???)
     */


}

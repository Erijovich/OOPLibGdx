package com.firsttest.game.somegame.units;


import com.firsttest.game.somegame.service.Position;
import com.firsttest.game.somegame.units.unitsabstract.BaseUnit;

public class Peasant extends BaseUnit {
    private static final int
            HP = 100,
            ARMOR = 20,
            INITIATIVE = 5,
            EVASION = 5;
    private int capacity; // размер мешка для стрел

    protected Peasant(Position pos) {super(HP, ARMOR, ArmorType.naked, INITIATIVE, EVASION, pos);}

    @Override
    public String getIcon () {
        if (isAlive()) return "\uD83D\uDC77\u200D";
        else return super.getIcon();
    }

    // todo крестьяне тоже будут ходить и сами искать кому что чинить

    public void takeStuff (){
    }
    public void giveStuff(BaseUnit ally){
        if (ally.unitType().equals("Snipr") || ally.unitType().equals("Arbst")) {

        }
    }

    @Override
    public String unitType() {
        return "Pesnt";
    }

    @Override
    public void action(Army armyAlly, Army armyEnemy) {
        int cnt = 0; // количество действий, которое крест совершает (дать стрелу, дать зелье маны, починить броню
        for (BaseUnit ally: armyAlly.getArmy()) {
            if (position.distance(ally.getPosition()) <= 3*Math.sqrt(2)) { // крестьянин работает в радиусе трёх клеток
                this.giveStuff(ally);
                cnt++;
            }
        }
        if (cnt < 2) move(findFarestUnit(armyAlly)); // иди к самому дальнему другу от тебя, если действия остались, а рядом никого нет (пока у него только два действия)
    }

}

package com.firsttest.game.somegame.units;



import com.firsttest.game.somegame.service.Field;
import com.firsttest.game.somegame.service.Position;
import com.firsttest.game.somegame.units.unitsabstract.BaseUnit;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Army {
    public String name;
    protected List<BaseUnit> units;
    protected boolean side;
    private static List<BaseUnit> priorityList = new ArrayList<>();

    public Army(String name, int size, boolean side) {
        this.name = name;
        this.side = side;
        units = new ArrayList<>(size);

        Random r = new Random();
        for (int i = 0; i < size; i++) {
            Position pos = Field.nextPosition(side);
            int nextUnit;
            if (side) nextUnit = r.nextInt(4); // первая тройка - одна сторона, крестьянин - ноль или десять
            else nextUnit = r.nextInt(10,14); // начиная с десятков - другая сторона. Можно добавлять юнитов

            switch (nextUnit) {
                case (1):
                    units.add (new Swordsman(pos)); break;
                case (2):
                    units.add (new Arbalester(pos)); break;
                case (3):
                    units.add (new Priest(pos)); break;

                case (11):
                    units.add (new Thief(pos)); break;
                case (12):
                    units.add (new Sniper(pos)); break;
                case (13):
                    units.add (new Wizard(pos)); break;
                default:
                    units.add (new Peasant(pos));
            }
        }
    }

    public int getSize(){return units.size();}

    /**
     * возвращает конкретного юнита из армии. Если индекса нет в списке - возвращает первого юнита из списка
     */
    public BaseUnit getUnit(int i){
        if (i < 0 || i > units.size()) i = 0;
        return units.get(i);
    }

    public List<BaseUnit> getArmy() {
        return units;
    }

    /**
     * если нет живых в армии - возвращает ложь
     * если хоть один живой - возвращает истину
     */
    public boolean isAlive() {
        for (BaseUnit unit: units) {
            if (unit.isAlive()) return true;
        }
        return false;
    }


    public static List<BaseUnit> getPriorityList() {return priorityList;}

    /**
     * этот метод надо вызвать один раз после создания всех армий
     * @param armies - любое количество армий можно добавлять.. но пока строго только две
     * fixme side должна быть числом, если хочу нескольько армий, например по четырём сторонам
     */
    public static void fillPriorityList(Army ... armies) {
        for (Army army : armies) priorityList.addAll(army.getArmy());
    }
    // remove from priority и add to priority

    public static void sortPriorityList() {
        priorityList.sort(new Comparator<BaseUnit>() {
            @Override
            public int compare(BaseUnit unit1, BaseUnit unit2) {
                if (unit1.getInitiative() == unit2.getInitiative()) {
                    return (int)(unit2.getHp()/unit2.getMaxHp() - unit1.getHp()/unit1.getMaxHp());
                }
                return unit1.getInitiative() - unit2.getInitiative();
            }
        });
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (BaseUnit unit : units) {
            sb.append(unit.getClass().getSimpleName())
                    .append(" ")
                    .append(unit.getName())
                    .append(".\n");
        }
        return sb.toString();
    }
}

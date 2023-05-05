package com.firsttest.game.somegame.units.unitsabstract;



import com.firsttest.game.somegame.service.Position;
import com.firsttest.game.somegame.service.UnitBehavior;
import com.firsttest.game.somegame.service.UnitNames;
import com.firsttest.game.somegame.units.Army;

import java.util.Random;

public abstract class BaseUnit implements UnitBehavior {
    protected enum ArmorType {naked, light, medium, heavy}
    public enum State {ready, busy, dead, powup}
    protected String name;
    protected float hp, maxHp;
    protected Position position;
    protected int ap; // количество брони, будет уменьшаться при получении урона (ломаться). крестьянин сможет чинить =) пока не задействовано
    protected ArmorType armType; // это буду использовать, если будет разный тип атаки. Может и избытчно. убрать что бы не закапываться?
    protected int initiative; // очерёдность хода , как в героях
    protected int evasion; // шанс уворота от атаки
    protected State state;

    protected static Random rnd;
    protected static final float[][] multMtrx;
    static {
        rnd = new Random();
        multMtrx = new float[][]{{0.5f,  1,     1.5f,  2   }, // строки - DamageType, столбцы - ArmorType
                                 {2,     1.5f,  1,     0.5f}, // множитель урона
                                 {1,     1,     1,     1   }};
    }


    protected BaseUnit(float hp, int ap, ArmorType armType, int initiative, int evasion, Position position) {
        this.name = setName();
        this.ap = ap;
        this.armType = armType;
        this.initiative = initiative;
        this.evasion = evasion;
        this.hp = this.maxHp = hp;
        this.position = position;
        this.state = State.ready;
    }

    /** Получаем рандомом имя юнита из enum UnitNames*/
    private String setName() {
        return UnitNames.values()[rnd.nextInt(UnitNames.values().length)].toString();
    }

    public Position getPosition() {return position;}
    public State getState() {return state;}
    public String getName() {return name;}
    public int getInitiative() {return initiative;}
    public float getHp() {return hp;}
    public float getMaxHp() {return maxHp;}

    /** статы юнита, классы наследника будут расширять этот метод своими уникальными значениями */
    @Override
    public String unitInfo() {
        return String.format("(%-5s) %-10s [%2d,%2d] HP:%4.0f/%-4.0f AP: %-3d %-6s  EVS: %-3d INT: %-1d",
                this.unitType(), this.name, this.position.getX(), this.position.getY(), this.hp, this.maxHp, this.ap, this.armType, this.evasion, this.initiative);
    }

    @Override
    public String unitStats() {
        return String.format("(%-5s) %-10s <%-1d %-5s> [%2d,%2d] HP:%4.0f/%-4.0f",
                this.unitType(), this.name, this.initiative, this.state, this.position.getX(), this.position.getY(), this.hp, this.maxHp);
    }

    @Override
    public String getIcon() {
        return "\uD83D\uDC80" ; // иконка мертвеца по умолчанию. Если жив, то будет родная, проверка в методе конкретного юнита
    }

    // пока сохраню старую версию вывода
//    @Override
//    public String unitInfo() {
//        return String.format("(%-5s) %-10s  position: [%2d,%2d]  HP:%4.0f/%-4.0f AP: %-3d  %-9s  Evasion: %-3d  Initiative: %-1d",
//                this.unitType(), this.name, this.position.getX(), this.position.getY(), this.hp, this.maxHp, this.ap, this.armType, this.evasion, this.initiative);
//    }
//    @Override
//    public String unitType() {
//        return "Notyp";
//    }

    // TODEL maybe
    @Override
    public void die() {
        System.out.println(String.format("%s %s tragically passed away", this.getClass().getSimpleName(), this.name));
    }

    @Override
    public void action(Army ally, Army enemy) {
        System.out.println(String.format("%s %s did a special dance to mock an enemy", this.getClass().getSimpleName(), this.name));
    }


    public Boolean isAlive (){return hp > 0;}

    /**
     * Получение юнитом урона
     * @param damage нанесённый конечный урон (отрицательно значение = лечение)
     */
    protected void takeDamage(float damage) {
        if (damage == 0) return; // промах
        if (damage < 0) {hp = Math.min(hp-damage, maxHp); return;} // лечение
        hp -= damage;
        if (hp <= 0) {state = State.dead;}
    }

    protected boolean checkPosition(int x, int pos_y) {
        if (x > 10 || pos_y > 10 || x < 1 || pos_y < 1)
            return false;
        // fixme !!!!!!! важно, проверка на столкновения нужна
//        for (BaseUnit hero: getAllyTeam())
//            if (hero.position.x == x && hero.position.y == pos_y)
//                return false;
        return true;
    }

    @Override
    public void move(BaseUnit target){
        int xd = target.getPosition().getX() - this.getPosition().getX();
        int yd = target.getPosition().getY() - this.getPosition().getY();

        int sx = 0, sy = 0;

        if (Math.abs(xd)>Math.abs(yd)){
            sx = (int) Math.signum(xd);
        } else sy = (int) Math.signum(yd);
        boolean flag = true;

        if (!checkPosition(this.getPosition().getX() + sx, this.getPosition().getY() + sy)) {
            sx = 0;
            sy = 0;
            flag = false;
            if (checkPosition(this.getPosition().getX() + sx, this.getPosition().getY() + sy + (int) Math.signum(yd))) {
                sy = (int) Math.signum(yd);
                flag = true;
            }
            if (!flag)
                if (checkPosition(this.getPosition().getX() + sx + (int) Math.signum(xd), this.getPosition().getY() + sy)) {
                    sx = (int) Math.signum(xd);
                    flag = true;
                }
        }

        this.getPosition().setX(sx);
        this.getPosition().setY(sy);
    }

    @Override
    public void recieveHelp() {

    }

    public BaseUnit findNearestUnit(Army army) {
        BaseUnit nearestUnit = null;
        for (int i = 0; i < army.getSize(); i++) { // проверка , что первый выбранный юнит не мёртв
            nearestUnit = army.getUnit(i);
            if (army.getUnit(i).isAlive()) break;
        }

        double distance = position.distance(nearestUnit.getPosition()); // fixme не нравится, как выглядит
        double minDistance = distance;
        for (int i = 1; i < army.getSize(); i++) {
            if (!army.getUnit(i).isAlive()) continue; // если рассматриваемый юнит мертв
            distance = position.distance(army.getUnit(i).getPosition());
            if (minDistance > distance) {
                minDistance = distance;
                nearestUnit = army.getUnit(i);
            }
        }
        return nearestUnit;
    }

    public BaseUnit findFarestUnit(Army army) {
        BaseUnit farestUnit = null;
        for (int i = 0; i < army.getSize(); i++) { // проверка , что первый выбранный юнит не мёртв
            farestUnit = army.getUnit(i);
            if (army.getUnit(i).isAlive()) break;
        }

        double distance = position.distance(farestUnit.getPosition()); // fixme не нравится, как выглядит
        double maxDistance = distance;
        for (int i = 1; i < army.getSize(); i++) {
            if (army.getUnit(i).getState() == State.dead) continue; // если рассматриваемый юнит мертв
            distance = position.distance(army.getUnit(i).getPosition());
            if (maxDistance < distance) {
                maxDistance = distance;
                farestUnit = army.getUnit(i);
            }
        }
        return farestUnit;
    }
}
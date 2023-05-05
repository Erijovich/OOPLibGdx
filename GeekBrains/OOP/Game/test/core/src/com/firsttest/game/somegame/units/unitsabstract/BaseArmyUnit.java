package com.firsttest.game.somegame.units.unitsabstract;


import com.firsttest.game.somegame.service.Position;

public abstract class BaseArmyUnit extends BaseUnit {
    /**
     * пока пусть будет так:
     * sharp урон 2х, 1.5х, 1х, 0,5х против брони соответственно нетброни, лёгкая, средняя и тяжёла
     * blunt - наоборот против тяжёлой брони самый большой урон - Чем броня легче, тем от дубины легче увернуться
     * magic - проотив всех множитлеь 1х
     */
    protected enum DamageType {blunt, sharp, magic}
    protected DamageType dmgType;
    protected int[] baseDmg; // диапазон сырого урона мин макс
    protected int critChance; // множитель для нанесения критического (полуторного или двойного, например) урона
    protected int accuracy; // множитель (процент) успешности атаки или промаха
    private static final float CRTMULT;

    static {CRTMULT = 1.5f;}

    protected BaseArmyUnit(float hp, int armor, ArmorType armType, int initiative, Position pos, DamageType dmgType,
                           int[] baseDmg, int critChance, int accuracy, int evasion) {
        super(hp, armor, armType, initiative, evasion, pos);
        this.baseDmg = baseDmg;
        this.dmgType = dmgType;
        this.critChance = critChance;
        this.accuracy = accuracy;
        this.evasion = evasion;
    }

    @Override
    public String unitInfo() {
        return  String.format("%s DMG: %2d-%-2d %-5s  CRT: %-3d ACC: %-3d",
                super.unitInfo(), this.baseDmg[0], this.baseDmg[1], this.dmgType, this.critChance, this.accuracy);
    }

    protected float calculateDamage(BaseUnit enemy){
        if (rnd.nextInt(1, 101)
                >= Integer.max(10, accuracy*(1-enemy.evasion/100))) // 10% гарантированный шанс попадания
            return 0f;
        float damage = rnd.nextInt(baseDmg[0], baseDmg[1]+1);
        float multiplier = 1;
        if (critChance >= rnd.nextInt(1, 101)) multiplier *= 1.5;

     /*     sharp урон 2х, 1.5х, 1х, 0,5х против брони соответственно нетброни, лёгкая, средняя и тяжёла
     *      blunt - наоборот против тяжёлой брони самый большой урон - Чем броня легче, тем от дубины легче увернуться
     *      magic - проотив всех множитлеь 1х */

        // это же матрица. наверняка как-то можно это в матричном типе реалитзовать, в строках тип брони
        // в столбцах тип урона. и для моиска значения множителя просто обращаться к матрице с двумя значениями
//        switch (enemy.armType){ // сам себе усложнил =)
//            case naked -> {switch (dmgType){
//                case blunt -> multiplier *=0.5;
//                case sharp -> multiplier *=2;
//                case magic -> multiplier *=1;}
//            }
//            case light -> {switch (dmgType){
//                case blunt -> multiplier *=1;
//                case sharp -> multiplier *=1.5;
//                case magic -> multiplier *=1;}
//            }
//            case medium -> {switch (dmgType){
//                case blunt -> multiplier *=1.5;
//                case sharp -> multiplier *=1;
//                case magic -> multiplier *=1;}
//            }
//            case heavy -> {switch (dmgType){
//                case blunt -> multiplier *=2;
//                case sharp -> multiplier *=0.5;
//                case magic -> multiplier *=1;}
//            }
//        }
//        // ну-с.. уменьшил)
//        multiplier *= multMtrx  [switch (dmgType){
//                                    case blunt -> 0;
//                                    case sharp -> 1;
//                                    case magic -> 2;}]
//                                [switch (enemy.armType){
//                                    case naked -> 0;
//                                    case light -> 1;
//                                    case medium -> 2;
//                                    case heavy -> 3;}];

        int a = -1, b = -1;
        switch (dmgType){
            case blunt: a = 0;  break;
            case sharp: a = 1;  break;
            case magic: a = 2; break;}
        switch (enemy.armType){
            case naked: b = 0; break;
            case light: b = 1; break;
            case medium: b = 2; break;
            case heavy: b = 3; break;}

        multiplier *= multMtrx [a][b];

        return damage*multiplier;
    }


    /**
     * пока метод неверный, нужен перерасчёт урона нанесённого
     * @param target
     * @param dmg
     */
//    public void attack (BaseUnit target, int[] dmg) {
//        int damage = BaseArmyUnit.rnd.nextInt(dmg[0],dmg[1]);
//        target.getDamage(damage);
//
//    }
}

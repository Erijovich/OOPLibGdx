package com.firsttest.game.somegame.service;



import com.firsttest.game.somegame.units.Army;
import com.firsttest.game.somegame.units.unitsabstract.BaseUnit;

import java.util.Scanner;

public class Game {

    private static final int ARMYSIZE = 10;
    private static final String ARMYONE = "Good Guys";
    private static final String ARMYTWO = "Bad Guys";

    public static void gameStart() {

//        System.out.println();

        // устанавливаем размер квадратного поля
        Field.setSize(ARMYSIZE);

        // генерируем две армии
        Army armyOne = new Army(ARMYONE, ARMYSIZE, true);
        Army armyTwo = new Army(ARMYTWO, ARMYSIZE, false);

        // подключаем сканер для считывания нажатий
        Scanner sc = new Scanner(System.in);

        // сортировка и заполнение очерёдности ходьбы юнитов в соответсвии с приоритетом
        Army.fillPriorityList(armyOne, armyTwo);

        // основной цикл игры
        while (armyOne.isAlive() & armyTwo.isAlive()) {

            // выставление очередности хода
            Army.sortPriorityList();

            // отрисовка юнитов
            View.view(Army.getPriorityList(), armyOne, armyTwo);

            // ждём нажатия, если латинская x - то выход из игры
            if (sc.nextLine().equals("x")) break;

            // последовательно выполняем действия каждым юнитом, если хп у него больше нуля
//            Army.getPriorityList().forEach(unit -> {
//                if (unit.isAlive()) {
//                    if (armyOne.getArmy().contains(unit)) unit.action(armyOne, armyTwo);
//                    else unit.action(armyTwo, armyOne);
//                }
//            });

            for (BaseUnit unit : Army.getPriorityList()) {
                if (unit.isAlive()) {
                    if (armyOne.getArmy().contains(unit)) unit.action(armyOne, armyTwo);
                    else unit.action(armyTwo, armyOne);
                }
            }
        }

        // финальные титры
        if (armyOne.isAlive() & armyTwo.isAlive()) System.out.println("game finished by user");
        else if (armyOne.isAlive())  System.out.println(ARMYONE + " wins");
        else System.out.println(ARMYTWO + " wins");
    }

}

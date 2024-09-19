package org.example;
//
//В качестве задачи предлагаю вам реализовать код для демонстрации парадокса Монти Холла
//и наглядно убедиться в верности парадокса
//(запустить игру в цикле на 1000 и вывести итоговый счет).
//Необходимо:
//Создать свой Java Maven или Gradle проект;
//Подключить зависимость lombok.
//Можно подумать о подключении какой-нибудь математической библиотеки для работы со статистикой
//Самостоятельно реализовать прикладную задачу;
//Сохранить результат в HashMap<шаг теста, результат>
//Вывести на экран статистику по победам и поражениям


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MontyHallParadox {

    @Data
    @AllArgsConstructor
    static class Result {
        boolean win;
        int chosenDoor;
        int revealedDoor;
    }

    public static void main(String[] args) {
        int totalGames = 1000;
        Map<Integer, Result> results = new HashMap<>();
        int wins = 0;
        int losses = 0;

        for (int i = 1; i <= totalGames; i++) {
            Result result = playGame();
            results.put(i, result);

            if (result.isWin()) {
                wins++;
            } else {
                losses++;
            }
        }

        System.out.println("Total wins: " + wins);
        System.out.println("Total losses: " + losses);
    }

//    Метод playGame(), моделирует игру известную как "Проблема Монти Холла".
//    В этой игре участвует ведущий, который знает, за какой дверью находится приз, и участник, который
//    выбирает одну из трех дверей.
//    Сначала метод генерирует случайное число (winningDoor) от 1 до 3,
//    представляющее выигрышную дверь, и выбранную игроком дверь (chosenDoor).
//    Затем метод случайным образом выбирает одну из оставшихся двух дверей,
//    за которой нет приза (revealedDoor). Эта дверь будет открыта ведущим после выбора игроком.
//    Затем метод определяет, будет ли игрок менять свой выбор двери (switchDoor) или оставить выбранную ранее.
//    Если игрок решает поменять дверь, метод снова случайным образом выбирает одну из двух оставшихся дверей,
//    и это становится новым выбором игрока.
//    Наконец, метод проверяет, выиграл ли игрок (win) после окончания игры, сравнивая выбранную дверь с
//    выигрышной и возвращает объект Result с указанием результата игры (выигрыш или проигрыш),
//    выбранной игроком двери и открытой ведущим двери.

    private static Result playGame() {
        Random random = ThreadLocalRandom.current();
        int winningDoor = random.nextInt(3) + 1;
        int chosenDoor = random.nextInt(3) + 1;

        int revealedDoor;
        do {
            revealedDoor = random.nextInt(3) + 1;
        } while (revealedDoor == winningDoor || revealedDoor == chosenDoor);

        boolean switchDoor = random.nextBoolean();

        if (switchDoor) {
            int newChosenDoor;
            do {
                newChosenDoor = random.nextInt(3) + 1;
            } while (newChosenDoor == chosenDoor || newChosenDoor == revealedDoor);

            chosenDoor = newChosenDoor;
        }

        boolean win = chosenDoor == winningDoor;

        return new Result(win, chosenDoor, revealedDoor);
    }
}


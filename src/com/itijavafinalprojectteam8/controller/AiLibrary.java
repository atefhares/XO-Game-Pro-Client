package com.itijavafinalprojectteam8.controller;

/**
 * @author Bassam, Esraa
 */

import java.util.*;

public class AiLibrary {

    public static ArrayList<Integer> playerPosition = new ArrayList<>(Collections.nCopies(1, 0));
    public static ArrayList<Integer> cpuPosition = new ArrayList<>(Collections.nCopies(1, 0));
    private static int counter = 0;

    public static void onPlayerMove(int playerPos) {

        move(playerPos, "player");
    }

    public static void onPlayer2Move(int playerPos) {

        move(playerPos, "cpu");
    }

    public static int onCpuMove() {
        int cpuPos = 0;

        cpuPos = cpuMove();
        System.out.println("cpu move is " + cpuPos);
        move(cpuPos, "cpu");
        return cpuPos;
    }

    public static int getWinner() {
        return checkWinner();
    }

    private static void move(int pos, String user) {
        if (user.equals("player")) {
            playerPosition.add(pos);
            counter++;
        } else if (user.equals("cpu")) {
            cpuPosition.add(pos);
        }
    }

    private static int checkWinner() {

        List topRow = Arrays.asList(1, 2, 3);
        List midRow = Arrays.asList(4, 5, 6);
        List botRow = Arrays.asList(7, 8, 9);
        List leftCol = Arrays.asList(1, 4, 7);
        List midCol = Arrays.asList(2, 5, 8);
        List rightCol = Arrays.asList(3, 6, 9);
        List cross1 = Arrays.asList(1, 5, 9);
        List cross2 = Arrays.asList(3, 5, 7);

        ArrayList<List> winning = new ArrayList<>();

        winning.add(topRow);
        winning.add(midRow);
        winning.add(botRow);
        winning.add(leftCol);
        winning.add(midCol);
        winning.add(rightCol);
        winning.add(cross1);
        winning.add(cross2);

        for (List l : winning) {
            if (playerPosition.containsAll(l)) {
                return 0;
            } else if (cpuPosition.containsAll(l)) {
                return 1;
            } else if (playerPosition.size() + cpuPosition.size() == 11) {
                return 2;
            }
        }
        return -1;
    }

    public static Integer cpuMove() {
        List topRow = Arrays.asList(1, 2, 3);
        List midRow = Arrays.asList(4, 5, 6);
        List botRow = Arrays.asList(7, 8, 9);
        List leftCol = Arrays.asList(1, 4, 7);
        List midCol = Arrays.asList(2, 5, 8);
        List rightCol = Arrays.asList(3, 6, 9);
        List cross1 = Arrays.asList(1, 5, 9);
        List cross2 = Arrays.asList(3, 5, 7);

        ArrayList<List> winning = new ArrayList<>();
        winning.add(topRow);
        winning.add(midRow);
        winning.add(botRow);
        winning.add(leftCol);
        winning.add(midCol);
        winning.add(rightCol);
        winning.add(cross1);
        winning.add(cross2);

        /*      switch case of turns counter:
        1st turn -> pick random winning pattern
        2nd turn-> check which pattern match first move and continue it if it's available
                   else pick anothor one that match if none just choose random
        3rd turn-> check which pattern match first & second pattern.
         */
        switch (counter) {
            default:
                break;
            case 1:
                Random rand = new Random();
                int firstMove = rand.nextInt(8);

                List l = winning.get(firstMove);
                int fvalue = (int) l.get((Integer) 1);

                Random randomf = new Random();

                while (playerPosition.contains(fvalue) || cpuPosition.contains(fvalue)) {
                    fvalue = randomf.nextInt(9) + 1;
                }

                return fvalue;
            /*================================================================================*/
            case 2:
                /*
                1- loop that compares 1st element in cpuPosition with all the winning positions
                2- when first match check if the position is available
                3- if not available continue the loop and find another one
                 */
                for (List f : winning) {
                    if (Objects.equals(cpuPosition.get(1), f.get(1))) {
                        int y = (Integer) f.get(1);

                        if (playerPosition.contains(y) || cpuPosition.contains(y)) {
                            continue;
                        }
                        return y;
                    }
                }
                Random rand1 = new Random();
                int x1 = rand1.nextInt(9) + 1;
                while (playerPosition.contains(x1) || cpuPosition.contains(x1)) {
                    x1 = rand1.nextInt(9) + 1;
                }
                return x1;

            case 3:
                for (List f : winning) {
                    if (Objects.equals(cpuPosition.get(1), f.get(1)) && Objects.equals(cpuPosition.get(2), f.get(2))) {

                        int z = (Integer) f.get(2);
                        if (playerPosition.contains(z) || cpuPosition.contains(z)) {
                            continue;
                        }
                        return z;
                    }
                }
                Random rand2 = new Random();
                int x2 = rand2.nextInt(9) + 1;
                while (playerPosition.contains(x2) || cpuPosition.contains(x2)) {
                    x2 = rand2.nextInt(9) + 1;
                }
                return x2;
        }
        Random rand3 = new Random();
        int x3 = rand3.nextInt(9) + 1;
        while (playerPosition.contains(x3) || cpuPosition.contains(x3)) {
            x3 = rand3.nextInt(9) + 1;
        }
        return x3;
    }

    public static void reset() {
        playerPosition = new ArrayList<>(Collections.nCopies(1, 0));
        cpuPosition = new ArrayList<>(Collections.nCopies(1, 0));
        counter = 0;
    }

    public static String[] getCurrentGameStateStr() {
        String[] str = new String[2];
        str[0] = "";
        str[1] = "";
        for (int pos : playerPosition) {
            if (pos != 0)
                str[0] += pos + ",";
        }
        for (int pos : cpuPosition) {
            if (pos != 0)
                str[1] += pos + ",";
        }
        return str;
    }
}
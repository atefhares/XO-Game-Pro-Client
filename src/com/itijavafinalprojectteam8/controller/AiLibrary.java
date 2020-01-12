/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itijavafinalprojectteam8.controller;

/**
 *
 *   @author Esraa
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class AiLibrary {

    private static char[][] gameBoard = {{' ', ' ', ' ',}, {' ', ' ', ' ',}, {' ', ' ', ' '}};
    private static ArrayList<Integer> playerPosition = new ArrayList<>(Collections.nCopies(1, 0));
    private static ArrayList<Integer> cpuPosition = new ArrayList<>(Collections.nCopies(1, 0));
    private static int counter = 0;

    public static void onPlayerMove(int playerPos) {
        move(gameBoard, playerPos, "player");
    }

    public static int onCpuMove() {
        int cpuPos = 0;
        move(gameBoard, cpuPos, "cpu");
        return cpuPos;
    }
    
  

    public static int getWinner() {
        return checkWinner(gameBoard);
    }

    private static void move(char[][] gameBoard, int pos, String user) {
        char symbol = ' ';
        if (user.equals("player")) {
            symbol = 'x';
            playerPosition.add(pos);
            counter++;
        } else if (user.equals("cpu")) {
            symbol = '0';
            cpuPosition.add(pos);
        }

        switch (pos) {
            default:
                break;
            case 1:
                gameBoard[0][0] = symbol;
                break;
            case 2:
                gameBoard[0][1] = symbol;
                break;
            case 3:
                gameBoard[0][2] = symbol;
                break;
            case 4:
                gameBoard[1][0] = symbol;
                break;
            case 5:
                gameBoard[1][1] = symbol;
                break;
            case 6:
                gameBoard[1][2] = symbol;
                break;
            case 7:
                gameBoard[2][0] = symbol;
                break;
            case 8:
                gameBoard[2][1] = symbol;
                break;
            case 9:
                gameBoard[2][2] = symbol;
                break;
        }
    }

    private static int checkWinner(char[][] gameBoard) {

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
                return (int) l.get((Integer) 1);

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

}

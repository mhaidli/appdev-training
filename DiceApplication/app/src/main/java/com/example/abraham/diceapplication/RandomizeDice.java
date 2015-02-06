package com.example.abraham.diceapplication;

/**
 * Created by Abraham on 12/26/2014.
 */

import java.util.Random;

public class RandomizeDice {
    public int rDice(/*int oldDice*/){
        int newDice;
        Random randomGenerator = new Random();
        int randomDice = randomGenerator.nextInt(6);
        newDice = randomDice + 1;
        return newDice;
    }
}

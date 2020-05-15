package au.edu.jcu.cp3406.WordSort.utilities;

import android.content.res.Resources;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static au.edu.jcu.cp3406.WordSort.utilities.Difficulty.EASY;
import static au.edu.jcu.cp3406.WordSort.utilities.Difficulty.HARD;
import static au.edu.jcu.cp3406.WordSort.utilities.Difficulty.MEDIUM;

public class GameBuilder {

    Resources res;
    String easyWords;
    Set<Integer> wordSet;
    Integer n;
    Words[] words;
    String word;

    //Game Builder constructor
    public GameBuilder() {
        this.wordSet = new HashSet<>();
    }

    //Method for determining difficulty selected
    public Game create(Difficulty difficulty) {
        Random random = new Random();
        switch (difficulty) {
            case EASY:
                n = 3;
                break;
            case MEDIUM:
                n = 6;
                break;
            case HARD:
                n = 9;
                break;
        }

        //for while loop to add words to a new set of unique strings
        words = new Words[n];
        String[] word = new String[n];

        for (int i = 0; i < n; i++) {
            while (wordSet.size() != n) {
                wordSet.add(random.nextInt(easyWords.length()));
            }
            words[i] = new Words(word);
        }
        return new Game(words);
    }



}

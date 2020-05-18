package au.edu.jcu.cp3406.WordSort.utilities;

import android.content.Context;
import android.content.res.Resources;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import au.edu.jcu.cp3406.WordSort.R;
import au.edu.jcu.cp3406.WordSort.WordActivity;

import static au.edu.jcu.cp3406.WordSort.utilities.Difficulty.EASY;
import static au.edu.jcu.cp3406.WordSort.utilities.Difficulty.HARD;
import static au.edu.jcu.cp3406.WordSort.utilities.Difficulty.MEDIUM;

public class GameBuilder {

    Resources res;
    String currentWordArray;
    Set<Integer> wordSet;
    Integer n;
    Words[] words;
    String word;

    //Game Builder constructor
    public GameBuilder() {
        this.wordSet = new HashSet<>();
    }

    public String setWordArray(String currentWordArray) {
        return currentWordArray;
    }

    //Method for determining difficulty selected
    public Game create(Difficulty difficulty) {
        Random random = new Random();
        switch (difficulty) {
            case EASY:
                n = 3;
                res.getStringArray(R.array.easy_words);
                break;
            case MEDIUM:
                n = 6;
                res.getStringArray(R.array.medium_words);
                break;
            case HARD:
                n = 9;
                res.getStringArray(R.array.hard_words);
                break;
        }

        //for while loop to add words to a new set of unique strings
        words = new Words[n];
        String[] word = new String[n];

        for (int i = 0; i < n; i++) {
            while (wordSet.size() != n) {
                wordSet.add(random.nextInt(currentWordArray.length()));
            }
            words[i] = new Words(word);
        }
        return new Game(words);
    }



}

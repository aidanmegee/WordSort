package au.edu.jcu.cp3406.WordSort.utilities;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Game {

    private int score = 0;
    private boolean gameOver;
    private Words[] words;
    private int wordsLeft;
    private int nextWord = 0;

    public Game(Words[] words) {
        this.words = words;
        this.wordsLeft = words.length;
    }

    //** Methods for game class **//
    public boolean isGameOver() {
        return wordsLeft < 1;
    }

    public String getScore() {
        return "Score " + score + "/" + words.length;
    }

    public Words next() {
        wordsLeft--;
        return words[nextWord++];
    }

    public Words getNextWord() {
        return words[nextWord];
    }

    public int count() {
        return score;
    }

    //method to shuffle letters of the current word
    private String wordShuffle(String word) {
        List<String> letters = Arrays.asList(word.split("")); //Assigns a list of letters split from given array
        Collections.shuffle(letters);
        String shuffled = "";
        for (String letter : letters) { //iterate over all letters in the List of type <String>
            shuffled += letter;
        }
        return shuffled;
    }
}

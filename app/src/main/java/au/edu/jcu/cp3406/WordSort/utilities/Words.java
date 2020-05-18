package au.edu.jcu.cp3406.WordSort.utilities;

import android.content.res.Resources;
import android.view.View;

import java.util.Random;

public class Words {

    private String word;
    private String[] words;
    Random randomNum;
    String currentWord;
    Resources res;


    public Words(String[] words) {
        this.words = words;
    }

    public boolean check(String guess) {
        return guess.equals(word);
    }

    public String[] getWords() {
        return words;
    }

    public void setWords(String[] words) {
        this.words = words;
    }

}

package au.edu.jcu.cp3406.WordSort.utilities;

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

    public boolean isGameOver() {
        return wordsLeft < 1;
    }

    public String updateScore() {
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
}

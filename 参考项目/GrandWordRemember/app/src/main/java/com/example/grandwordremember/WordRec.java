package com.example.grandwordremember;

public class WordRec {
    private int id;
    private String word;
    private String explanation;
    private int level;
    WordRec() {}
    WordRec(int id, String word, String explanation, int level) {
        this.id = id;
        this.word = word;
        this.explanation = explanation;
        this.level = level;
    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getWord() { return word; }
    public void setWord(String word) { this.word = word; }
    public String getExplanation() { return explanation; }
    public void setExplanation(String explanation) { this.explanation = explanation; }
    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }
}

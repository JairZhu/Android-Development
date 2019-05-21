package com.example.grandwordremember;

public class TestRec {
    private int _id;
    private String word;
    private int level;
    private int test_count;
    private int correct_count;
    public TestRec(int id, String word, int level, int test_count, int correct_count) {
        this._id = id;
        this.word = word;
        this.level = level;
        this.test_count = test_count;
        this.correct_count = correct_count;
    }
    public int get_id() { return _id; }
    public void set_id(int id) { this._id = id; }
    public String getWord() { return word; }
    public void  setWord(String word) { this.word = word; }
    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }
    public int getTest_count() { return test_count; }
    public void setTest_count(int test_count) { this.test_count = test_count; }
    public int getCorrect_count() { return correct_count; }
    public void setCorrect_count(int correct_count) { this.correct_count = correct_count; }
}

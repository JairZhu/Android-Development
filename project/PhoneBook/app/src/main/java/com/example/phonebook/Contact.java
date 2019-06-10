package com.example.phonebook;

public class Contact {
    private String number, name, abbribution, birthday;
    private char sortkey;
    private int whitelist;
    public String getNumber() {
        return number;
    }
    public String getName() {
        return name;
    }
    public String getAbbribution() {
        return abbribution;
    }
    public String getBirthday() {
        return birthday;
    }
    public int getWhitelist() { return whitelist; }
    public String getSortkey() { return sortkey + ""; }

    public void setSortkey(char sortkey) { this.sortkey = sortkey; }
    public void setAbbribution(String abbribution) {
        this.abbribution = abbribution;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setBirthday(String birthday) { this.birthday = birthday; }
    public void setWhitelist(int in) {
        this.whitelist = in;
    }
}

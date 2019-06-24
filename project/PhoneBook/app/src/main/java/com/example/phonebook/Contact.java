package com.example.phonebook;

public class Contact {
    private String number, name, abbribution, birthday, sortkey;
    private int whitelist;
    public Contact(String index, String name) {
        this.sortkey = index;
        this.name = name;
    }
    public Contact() {}
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
    public int getWhitelist() {
        return whitelist;
    }
    public String getIndex() {
        return sortkey + "";
    }

    public void setSortkey(String sortkey) {
        this.sortkey = sortkey;
    }
    public void setAbbribution(String abbribution) {
        this.abbribution = abbribution;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public void setName(String name) {
        this.name = name;
        if (name == null || name.isEmpty())
            sortkey = "#";
        else {
            String key = name.substring(0, 1);
            if (key.matches("[A-Z]") || key.matches("[a-z]"))
                sortkey = key.toUpperCase();
            else
                sortkey = "#";
        }
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public void setWhitelist(int in) {
        this.whitelist = in;
    }
}

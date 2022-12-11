package com.example.prepbites;

import java.io.Serializable;

public class Prep implements Serializable {
    public String name, subject, date, repeatDays, task;

    public Prep() {
    }

    public Prep(String name, String subject, String date, String repeatDays, String task) {
        this.name = name;
        this.subject = subject;
        this.date = date;
        this.repeatDays = repeatDays;
        this.task = task;
    }
}

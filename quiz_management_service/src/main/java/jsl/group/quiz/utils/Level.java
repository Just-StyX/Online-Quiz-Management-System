package jsl.group.quiz.utils;

public enum Level {
    EASY("easy"), MEDIUM("medium"), HARD("hard");

    public String level;

    Level(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}

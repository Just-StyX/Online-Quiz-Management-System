package jsl.group.quiz.models.scores;

import jsl.group.quiz.models.entities.UserScoreAndReview;

import java.util.ArrayList;
import java.util.List;

public class ScoreBoard {
    private final List<Observer> observers = new ArrayList<>();

    private ScoreBoard() { }
    public static ScoreBoard getInstance() { return new ScoreBoard(); }
    public void addObserver(Observer observer) {
        observers.add(observer);
    }
    public List<String> notification(UserScoreAndReview userScoreAndReview) {
        List<String> results = new ArrayList<>();
        for (Observer observer: observers) {
            String message = observer.update(userScoreAndReview);
            results.add(message);
        }
        return results;
    }
    public List<String> valueChanges(UserScoreAndReview userScoreAndReview) {
        return notification(userScoreAndReview);
    }
}

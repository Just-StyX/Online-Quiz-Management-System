package jsl.group.quiz.models.scores;

import jsl.group.quiz.models.entities.UserScoreAndReview;

public interface Observer {
    String update(UserScoreAndReview userScoreAndReview);
}

package jsl.group.quiz.models.entities;

import java.util.ArrayList;
import java.util.List;

public class UserScoreAndReview {
    private final String username;
    private final String subject;
    private final List<String> questionIds;

    public static class Builder {
        private String username;
        private String subject;
        private List<String> questionIds = new ArrayList<>();

        public Builder username(String username) {
            this.username = username;
            return this;
        }
        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }
        public Builder addQuestionIds(List<String> questionIds) {
            this.questionIds = questionIds;
            return this;
        }
        public UserScoreAndReview build() {
            return new UserScoreAndReview(this);
        }
    }
    private UserScoreAndReview(Builder builder) {
        this.username = builder.username;
        this.subject = builder.subject;
        this.questionIds = builder.questionIds;
    }

    public String getUsername() {
        return username;
    }

    public String getSubject() {
        return subject;
    }

    public List<String> getQuestionIds() {
        return questionIds;
    }
}

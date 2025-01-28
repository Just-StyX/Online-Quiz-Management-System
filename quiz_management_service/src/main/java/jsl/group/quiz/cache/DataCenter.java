package jsl.group.quiz.cache;

import jsl.group.quiz.models.entities.QuizAnswer;
import jsl.group.quiz.models.entities.QuizQuestion;
import jsl.group.quiz.utils.structures.PositionalLinkedList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataCenter {
    public Map<String, List<QuizAnswer>> quizAnswers = new HashMap<>();
    public Map<String, PositionalLinkedList<QuizQuestion>> quizQuestions = new HashMap<>();
    public Map<String, Map<String, Character>> userAnswerChoices = new HashMap<>(); // username, qId, answerChoices
}

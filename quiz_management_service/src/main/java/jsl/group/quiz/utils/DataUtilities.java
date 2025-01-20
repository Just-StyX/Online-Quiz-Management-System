package jsl.group.quiz.utils;

import java.util.Map;
import java.util.TreeMap;

public class DataUtilities {
    public static Map<String, String> emptyData() {
        Map<String, String> noData = new TreeMap<>();
        noData.put("questionId", "");
        noData.put("question", "No data found for question");
        noData.put("answer", "");
        noData.put("options", "");
        noData.put("subject", "");
        noData.put("points", "0.0");
        noData.put("level", "");
        return noData;
    }
    public static String getQuestionLocation(Map<String, String> question, boolean show, String message, boolean messageBool) {
        return String.format("/admin/question/updates?questionId=%s&question=%s&answer=%s&options=%s&subject=%s&points=%s&level=%s&show=%s&message=%s&messageBool=%s",
                question.get("questionId"), question.get("question"), question.get("answer"), question.get("options"),
                question.get("subject"), question.get("points"), question.get("level"), show, message, messageBool);
    }
}

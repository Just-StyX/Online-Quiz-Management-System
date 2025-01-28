package gg.jte.generated.ondemand;
@SuppressWarnings("unchecked")
public final class JtequizGenerated {
	public static final String JTE_NAME = "quiz.jte";
	public static final int[] JTE_LINE_INFO = {0,0,0,0,0,9,9,9,9,9,9,9,11,11,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,14,14,14,14,14,14,16,16,18,18,20,20,21,21,23,23,27,27,27,0,1,2,3,4,4,4,4};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, jsl.group.quiz.models.entities.QuizQuestion question, java.util.Map<String, Character> map, java.util.Map<String, Character> answerMap, boolean first, boolean last) {
		jteOutput.writeContent("\n<html lang=\"en\">\n<body>\n    <div>\n        <h1>Question: ");
		jteOutput.setContext("h1", null);
		jteOutput.writeUserContent(question.question());
		jteOutput.writeContent(" <span>[");
		jteOutput.setContext("span", null);
		jteOutput.writeUserContent(question.points());
		jteOutput.writeContent(" point(s)]</span></h1>\n        <div>\n            ");
		for (java.util.Map.Entry<Character, String> entry : question.options().entrySet()) {
			jteOutput.writeContent("\n                <ul style=\"list-style-type: none;\">\n                    <li><button > <a href=\"/quiz/question/quiz?id=");
			jteOutput.setContext("a", "href");
			jteOutput.writeUserContent(question.questionId());
			jteOutput.setContext("a", null);
			jteOutput.writeContent("&choice=");
			jteOutput.setContext("a", "href");
			jteOutput.writeUserContent(entry.getKey());
			jteOutput.setContext("a", null);
			jteOutput.writeContent("&first=");
			jteOutput.setContext("a", "href");
			jteOutput.writeUserContent(first);
			jteOutput.setContext("a", null);
			jteOutput.writeContent("&last=");
			jteOutput.setContext("a", "href");
			jteOutput.writeUserContent(last);
			jteOutput.setContext("a", null);
			jteOutput.writeContent("\">\n                                ");
			jteOutput.setContext("a", null);
			jteOutput.writeUserContent(entry.getKey());
			jteOutput.writeContent("</a> </button> <span>");
			jteOutput.setContext("span", null);
			jteOutput.writeUserContent(entry.getValue());
			jteOutput.writeContent("</span></li>\n                </ul>\n            ");
		}
		jteOutput.writeContent("\n        </div>\n        ");
		if (!first) {
			jteOutput.writeContent("\n            <button > <a href=\"/quiz/question/quiz?previous=true\">Previous</a> </button>\n        ");
		}
		jteOutput.writeContent("\n        ");
		if (!last) {
			jteOutput.writeContent("\n            <button> <a href=\"/quiz/question/quiz?next=true\">Next</a> </button>\n        ");
		}
		jteOutput.writeContent("\n        <button> <a href=\"/quiz/question/end\">End Quiz</a> </button>\n    </div>\n</body>\n</html>");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		jsl.group.quiz.models.entities.QuizQuestion question = (jsl.group.quiz.models.entities.QuizQuestion)params.get("question");
		java.util.Map<String, Character> map = (java.util.Map<String, Character>)params.getOrDefault("map", new java.util.HashMap<>());
		java.util.Map<String, Character> answerMap = (java.util.Map<String, Character>)params.getOrDefault("answerMap", new java.util.HashMap<>());
		boolean first = (boolean)params.get("first");
		boolean last = (boolean)params.get("last");
		render(jteOutput, jteHtmlInterceptor, question, map, answerMap, first, last);
	}
}

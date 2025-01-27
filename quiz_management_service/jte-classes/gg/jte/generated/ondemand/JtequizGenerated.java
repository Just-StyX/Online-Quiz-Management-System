package gg.jte.generated.ondemand;
@SuppressWarnings("unchecked")
public final class JtequizGenerated {
	public static final String JTE_NAME = "quiz.jte";
	public static final int[] JTE_LINE_INFO = {0,0,0,0,0,8,8,8,8,8,8,8,10,10,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,14,14,16,16,18,18,19,19,21,21,22,22,22,22,22,22,22,22,22,25,25,25,0,1,2,3,3,3,3};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, jsl.group.quiz.models.QuizQuestion question, java.util.Map<String, Character> map, boolean first, boolean last) {
		jteOutput.writeContent("\n<html lang=\"en\">\n<body>\n    <div>\n        <h1>Question: ");
		jteOutput.setContext("h1", null);
		jteOutput.writeUserContent(question.question());
		jteOutput.writeContent(" <span>[");
		jteOutput.setContext("span", null);
		jteOutput.writeUserContent(question.points());
		jteOutput.writeContent(" point(s)]</span></h1>\n        <div>\n            ");
		for (java.util.Map.Entry<Character, String> entry : question.options().entrySet()) {
			jteOutput.writeContent("\n                <ul style=\"list-style-type: none;\">\n                    <li><button");
			var __jte_html_attribute_0 = entry.getKey();
			if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_0)) {
				jteOutput.writeContent(" value=\"");
				jteOutput.setContext("button", "value");
				jteOutput.writeUserContent(__jte_html_attribute_0);
				jteOutput.setContext("button", null);
				jteOutput.writeContent("\"");
			}
			jteOutput.writeContent(" onclick=\"'");
			jteOutput.setContext("button", "onclick");
			jteOutput.writeUserContent(map.put(question.questionId(), entry.getKey()));
			jteOutput.setContext("button", null);
			jteOutput.writeContent("'\">");
			jteOutput.setContext("button", null);
			jteOutput.writeUserContent(entry.getKey());
			jteOutput.writeContent("</button> <span>");
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
		jteOutput.writeContent("\n        <button");
		var __jte_html_attribute_1 = map.entrySet().toString();
		if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_1)) {
			jteOutput.writeContent(" value=\"");
			jteOutput.setContext("button", "value");
			jteOutput.writeUserContent(__jte_html_attribute_1);
			jteOutput.setContext("button", null);
			jteOutput.writeContent("\"");
		}
		jteOutput.writeContent("> <a href=\"/quiz/question/end\">End Quiz</a> </button>\n    </div>\n</body>\n</html>");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		jsl.group.quiz.models.QuizQuestion question = (jsl.group.quiz.models.QuizQuestion)params.get("question");
		java.util.Map<String, Character> map = (java.util.Map<String, Character>)params.getOrDefault("map", new java.util.HashMap<>());
		boolean first = (boolean)params.get("first");
		boolean last = (boolean)params.get("last");
		render(jteOutput, jteHtmlInterceptor, question, map, first, last);
	}
}

package gg.jte.generated.ondemand;
@SuppressWarnings("unchecked")
public final class Jteedit_question_formGenerated {
	public static final String JTE_NAME = "edit_question_form.jte";
	public static final int[] JTE_LINE_INFO = {0,0,0,0,0,16,16,16,18,18,18,18,22,22,22,26,26,26,34,35,35,35,42,42,42,42,42,42,42,42,42,46,46,46,46,46,46,46,46,46,51,51,51,58,58,60,60,60,0,1,1,1,1};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, boolean show, jsl.group.quiz.models.Question question) {
		jteOutput.writeContent("\n<html lang=\"en\">\n<head>\n    <meta charset=\"UTF-8\">\n    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n    <title>Question Form</title>\n</head>\n<body>\n    <form action=\"/admin/question/search\" method=\"post\">\n        <label for=\"search-question\">\n            <textarea id=\"search-question\" name=\"search-question\" rows=\"3\" cols=\"50\" >Enter question fully</textarea>\n        </label>\n        <button type=\"submit\">Search Question</button>\n    </form>\n    ");
		if (show) {
			jteOutput.writeContent("\n        <div>\n            <form action=\"/admin/question/updates?id=");
			jteOutput.setContext("form", "action");
			jteOutput.writeUserContent(question.questionId());
			jteOutput.setContext("form", null);
			jteOutput.writeContent("\" method=\"post\">\n                <h2>Create a New Question</h2>\n                <div>\n                    <label for=\"question\">Question:</label>\n                    <textarea id=\"question\" name=\"question\" rows=\"3\" cols=\"50\" >");
			jteOutput.setContext("textarea", null);
			jteOutput.writeUserContent(question.question());
			jteOutput.writeContent("</textarea>\n                </div>\n                <div>\n                    <label for=\"answer\">Answer:</label>\n                    <textarea id=\"answer\" name=\"answer\" rows=\"2\" cols=\"30\">");
			jteOutput.setContext("textarea", null);
			jteOutput.writeUserContent(question.answer());
			jteOutput.writeContent("</textarea>\n                </div>\n\n                <h3>Section Options</h3>\n                <div>\n                    <label for=\"options\">Options:</label>\n                    <h4>May be left blank if open-ended question. Make sure there are no trailing ';'.</h4>\n                    <textarea id=\"options\" name=\"options\" rows=\"2\" cols=\"40\" placeholder=\"Example: A=option;B=option;C=option\">\n                        ");
			jteOutput.setContext("textarea", null);
			jteOutput.writeUserContent(question.options().entrySet().stream().map(entry -> entry.getKey() + "=" + entry.getValue() + ";")
                            .collect(java.util.stream.Collectors.joining()).trim());
			jteOutput.writeContent("\n                    </textarea>\n                </div>\n\n                <h3>Question Details</h3>\n                <div>\n                    <label for=\"subject\">Subject:</label>\n                    <input type=\"text\" id=\"subject\" name=\"subject\"");
			var __jte_html_attribute_0 = question.subject();
			if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_0)) {
				jteOutput.writeContent(" value=\"");
				jteOutput.setContext("input", "value");
				jteOutput.writeUserContent(__jte_html_attribute_0);
				jteOutput.setContext("input", null);
				jteOutput.writeContent("\"");
			}
			jteOutput.writeContent(">\n                </div>\n                <div>\n                    <label for=\"points\">Points:</label>\n                    <input type=\"number\" id=\"points\" name=\"points\"");
			var __jte_html_attribute_1 = question.points();
			if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_1)) {
				jteOutput.writeContent(" value=\"");
				jteOutput.setContext("input", "value");
				jteOutput.writeUserContent(__jte_html_attribute_1);
				jteOutput.setContext("input", null);
				jteOutput.writeContent("\"");
			}
			jteOutput.writeContent(">\n                </div>\n                <div>\n                    <label for=\"level\">Level:</label>\n                    <textarea id=\"level\" name=\"level\" rows=\"2\" cols=\"30\" placeholder=\"Example: easy, medium, hard\">\n                        ");
			jteOutput.setContext("textarea", null);
			jteOutput.writeUserContent(question.level());
			jteOutput.writeContent("\n                    </textarea>\n                </div>\n\n                <button type=\"submit\">Edit Question</button>\n            </form>\n        </div>\n    ");
		}
		jteOutput.writeContent("\n</body>\n</html>");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		boolean show = (boolean)params.get("show");
		jsl.group.quiz.models.Question question = (jsl.group.quiz.models.Question)params.get("question");
		render(jteOutput, jteHtmlInterceptor, show, question);
	}
}

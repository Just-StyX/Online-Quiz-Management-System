package gg.jte.generated.ondemand;
@SuppressWarnings("unchecked")
public final class Jtequestion_formGenerated {
	public static final String JTE_NAME = "question_form.jte";
	public static final int[] JTE_LINE_INFO = {0,0,0,0,0,10,10,10,11,11,11,12,12,49,49,49,0,1,1,1,1};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, boolean createdBool, String created) {
		jteOutput.writeContent("\n<html lang=\"en\">\n<head>\n    <meta charset=\"UTF-8\">\n    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n    <title>Question Form</title>\n</head>\n<body>\n    ");
		if (createdBool) {
			jteOutput.writeContent("\n        <h4>");
			jteOutput.setContext("h4", null);
			jteOutput.writeUserContent(created);
			jteOutput.writeContent("</h4>\n    ");
		}
		jteOutput.writeContent("\n    <form action=\"/admin/question\" method=\"post\">\n        <h2>Create a New Question</h2>\n        <div>\n            <label for=\"question\">Question:</label>\n            <textarea id=\"question\" name=\"question\" rows=\"3\" cols=\"50\"></textarea>\n        </div>\n        <div>\n            <label for=\"answer\">Answer:</label>\n            <textarea id=\"answer\" name=\"answer\" rows=\"2\" cols=\"30\"></textarea>\n        </div>\n\n        <h3>Section Options</h3>\n        <div>\n            <label for=\"options\">Options:</label>\n            <h4>May be left blank if open-ended question</h4>\n            <textarea id=\"options\" name=\"options\" rows=\"2\" cols=\"40\" placeholder=\"Example: A=option;B=option;C=option\"></textarea>\n        </div>\n\n        <h3>Question Details</h3>\n        <div>\n            <label for=\"subject\">Subject:</label>\n            <input type=\"text\" id=\"subject\" name=\"subject\">\n        </div>\n        <div>\n            <label for=\"points\">Points:</label>\n            <input type=\"number\" id=\"points\" name=\"points\">\n        </div>\n        <div>\n            <label for=\"level\">Level:</label>\n            <textarea id=\"level\" name=\"level\" rows=\"2\" cols=\"30\" placeholder=\"Example: easy, medium, hard\"></textarea>\n        </div>\n\n        <button type=\"submit\">Create Question</button>\n    </form>\n    <button type=\"button\"> <a href=\"/admin\"> Back </a> </button>\n</body>\n</html>");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		boolean createdBool = (boolean)params.get("createdBool");
		String created = (String)params.get("created");
		render(jteOutput, jteHtmlInterceptor, createdBool, created);
	}
}

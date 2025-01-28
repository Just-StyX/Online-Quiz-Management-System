package gg.jte.generated.ondemand;
@SuppressWarnings("unchecked")
public final class JteuserGenerated {
	public static final String JTE_NAME = "user.jte";
	public static final int[] JTE_LINE_INFO = {0,0,0,0,0,5,5,5,5,5,5,5,7,7,8,8,8,9,9,13,13,13,0,1,1,1,1};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, jsl.group.quiz.models.entities.FoundUser foundUser, java.util.List<String> result) {
		jteOutput.writeContent("\n<html lang=\"en\">\n<body>\n    <h1>Hello ");
		jteOutput.setContext("h1", null);
		jteOutput.writeUserContent(foundUser.username());
		jteOutput.writeContent(" ");
		jteOutput.setContext("h1", null);
		jteOutput.writeUserContent(foundUser.firstName());
		jteOutput.writeContent("</h1>\n    <div>\n        ");
		for (String string: result) {
			jteOutput.writeContent("\n            <p>");
			jteOutput.setContext("p", null);
			jteOutput.writeUserContent(string);
			jteOutput.writeContent("</p>\n        ");
		}
		jteOutput.writeContent("\n    </div>\n    <button> <a href=\"/quiz/question/quiz?subject=pilot&limit=5\"> Start Quiz</a> </button>\n</body>\n</html>");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		jsl.group.quiz.models.entities.FoundUser foundUser = (jsl.group.quiz.models.entities.FoundUser)params.get("foundUser");
		java.util.List<String> result = (java.util.List<String>)params.getOrDefault("result", new java.util.ArrayList<>());
		render(jteOutput, jteHtmlInterceptor, foundUser, result);
	}
}

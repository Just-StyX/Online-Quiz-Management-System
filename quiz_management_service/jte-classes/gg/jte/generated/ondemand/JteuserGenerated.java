package gg.jte.generated.ondemand;
@SuppressWarnings("unchecked")
public final class JteuserGenerated {
	public static final String JTE_NAME = "user.jte";
	public static final int[] JTE_LINE_INFO = {0,0,0,0,0,4,4,4,4,4,4,4,7,7,7,0,0,0,0};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, jsl.group.quiz.models.FoundUser foundUser) {
		jteOutput.writeContent("\n<html lang=\"en\">\n<body>\n<h1>Hello ");
		jteOutput.setContext("h1", null);
		jteOutput.writeUserContent(foundUser.username());
		jteOutput.writeContent(" ");
		jteOutput.setContext("h1", null);
		jteOutput.writeUserContent(foundUser.firstName());
		jteOutput.writeContent("</h1>\n<button> <a href=\"/quiz/question/quiz?subject=pilot&limit=5\"> Start Quiz</a> </button>\n</body>\n</html>");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		jsl.group.quiz.models.FoundUser foundUser = (jsl.group.quiz.models.FoundUser)params.get("foundUser");
		render(jteOutput, jteHtmlInterceptor, foundUser);
	}
}

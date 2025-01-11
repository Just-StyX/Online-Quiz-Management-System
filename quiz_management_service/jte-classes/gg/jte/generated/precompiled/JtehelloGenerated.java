package gg.jte.generated.precompiled;
@SuppressWarnings("unchecked")
public final class JtehelloGenerated {
	public static final String JTE_NAME = "hello.jte";
	public static final int[] JTE_LINE_INFO = {0,0,0,0,0,4,4,4,4,4,4,4,12,12,12,0,0,0,0};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, jsl.group.quiz.models.UserLogin user) {
		jteOutput.writeContent("\n<html lang=\"en\">\n<body>\n<h1>Hello ");
		jteOutput.setContext("h1", null);
		jteOutput.writeUserContent(user.username());
		jteOutput.writeContent(" ");
		jteOutput.setContext("h1", null);
		jteOutput.writeUserContent(user.message());
		jteOutput.writeContent("</h1>\n<form>\n    <label>Name: </label>\n    <label>\n        <input type=\"text\">\n    </label>\n</form>\n</body>\n</html>");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		jsl.group.quiz.models.UserLogin user = (jsl.group.quiz.models.UserLogin)params.get("user");
		render(jteOutput, jteHtmlInterceptor, user);
	}
}

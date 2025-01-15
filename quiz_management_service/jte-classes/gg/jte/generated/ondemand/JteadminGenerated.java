package gg.jte.generated.ondemand;
@SuppressWarnings("unchecked")
public final class JteadminGenerated {
	public static final String JTE_NAME = "admin.jte";
	public static final int[] JTE_LINE_INFO = {0,0,0,0,0,4,4,4,4,4,4,4,12,12,12,0,0,0,0};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, jsl.group.quiz.models.FoundUser foundUser) {
		jteOutput.writeContent("\n<html lang=\"en\">\n    <body>\n        <h1>Hello ");
		jteOutput.setContext("h1", null);
		jteOutput.writeUserContent(foundUser.username());
		jteOutput.writeContent(" ");
		jteOutput.setContext("h1", null);
		jteOutput.writeUserContent(foundUser.firstName());
		jteOutput.writeContent("</h1>\n        <div>\n            <button type=\"button\"> <a href=\"/admin/question\"> Create Question </a> </button>\n            <button type=\"button\"> <a href=\"/admin/question/updates\"> Update Question </a> </button>\n            <button type=\"button\"> <a href=\"/admin/question/quest\"> Get Question </a> </button>\n            <button type=\"button\"> <a href=\"/admin/question/remove\"> Delete Question </a> </button>\n        </div>\n    </body>\n</html>");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		jsl.group.quiz.models.FoundUser foundUser = (jsl.group.quiz.models.FoundUser)params.get("foundUser");
		render(jteOutput, jteHtmlInterceptor, foundUser);
	}
}

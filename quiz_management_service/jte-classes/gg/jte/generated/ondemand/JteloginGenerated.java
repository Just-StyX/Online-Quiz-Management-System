package gg.jte.generated.ondemand;
@SuppressWarnings("unchecked")
public final class JteloginGenerated {
	public static final String JTE_NAME = "login.jte";
	public static final int[] JTE_LINE_INFO = {0,0,0,0,0,8,8,8,8,8,8,8,8,8,8,16,16,16,0,0,0,0};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, String email) {
		jteOutput.writeContent("\n\n<html lang=\"en\">\n<body>\n<h1>User Login</h1>\n<form method=\"post\" action=\"/login\">\n    <label for=\"email\">Email:</label><br>\n    <input type=\"email\" id=\"email\" name=\"email\"");
		var __jte_html_attribute_0 = email;
		if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_0)) {
			jteOutput.writeContent(" value=\"");
			jteOutput.setContext("input", "value");
			jteOutput.writeUserContent(__jte_html_attribute_0);
			jteOutput.setContext("input", null);
			jteOutput.writeContent("\"");
		}
		jteOutput.writeContent(" required><br><br>\n\n    <label for=\"password\">Password:</label><br>\n    <input type=\"password\" id=\"password\" name=\"password\" required><br><br>\n\n    <input type=\"submit\" value=\"Submit\">\n</form>\n</body>\n</html>");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		String email = (String)params.get("email");
		render(jteOutput, jteHtmlInterceptor, email);
	}
}

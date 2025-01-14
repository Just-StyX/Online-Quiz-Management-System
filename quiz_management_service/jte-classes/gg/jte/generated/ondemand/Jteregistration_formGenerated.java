package gg.jte.generated.ondemand;
@SuppressWarnings("unchecked")
public final class Jteregistration_formGenerated {
	public static final String JTE_NAME = "registration_form.jte";
	public static final int[] JTE_LINE_INFO = {26,26,26,26,26,26,26,26,26,26,26,26};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor) {
		jteOutput.writeContent("<html lang=\"en\">\n<body>\n<h1>User Registration</h1>\n<form method=\"post\" action=\"/register\">\n    <label for=\"username\">Username:</label><br>\n    <input type=\"text\" id=\"username\" name=\"username\" required><br><br>\n\n    <label for=\"password\">Password:</label><br>\n    <input type=\"password\" id=\"password\" name=\"password\" required><br><br>\n\n    <label for=\"email\">Email:</label><br>\n    <input type=\"email\" id=\"email\" name=\"email\" required><br><br>\n\n    <label for=\"firstName\">First Name:</label><br>\n    <input type=\"text\" id=\"firstName\" name=\"firstName\" required><br><br>\n\n    <label for=\"middleName\">Middle Name:</label><br>\n    <input type=\"text\" id=\"middleName\" name=\"middleName\"><br><br>\n\n    <label for=\"lastName\">Last Name:</label><br>\n    <input type=\"text\" id=\"lastName\" name=\"lastName\" required><br><br>\n\n    <input type=\"submit\" value=\"Submit\">\n</form>\n<button> <a href=\"/login\">Login</a> </button>\n</body>\n</html>");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		render(jteOutput, jteHtmlInterceptor);
	}
}

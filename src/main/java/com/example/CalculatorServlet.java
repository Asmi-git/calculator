package com.example;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * HTTP servlet that delegates arithmetic operations to {@link Calculator}.
 *
 * <p>Accepts GET requests to {@code /calculate} with the following query parameters:
 * <ul>
 *   <li>{@code a} – first operand (numeric)</li>
 *   <li>{@code b} – second operand (numeric)</li>
 *   <li>{@code op} – operation: {@code add}, {@code subtract}, {@code multiply}, or {@code divide}</li>
 * </ul>
 *
 * <p>Responds with plain-text HTML showing the result, or HTTP 400 on invalid input.
 */
@WebServlet("/calculate")
public class CalculatorServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final Calculator calculator = new Calculator();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("text/html;charset=UTF-8");

        String paramA  = request.getParameter("a");
        String paramB  = request.getParameter("b");
        String paramOp = request.getParameter("op");

        // --- Input presence validation ---
        if (isNullOrBlank(paramA) || isNullOrBlank(paramB) || isNullOrBlank(paramOp)) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST,
                    "Missing parameter(s). Please supply 'a', 'b', and 'op'.");
            return;
        }

        // --- Numeric parsing ---
        double a;
        double b;
        try {
            a = Double.parseDouble(paramA.trim());
        } catch (NumberFormatException e) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST,
                    "Parameter 'a' is not a valid number: " + escapeHtml(paramA));
            return;
        }
        try {
            b = Double.parseDouble(paramB.trim());
        } catch (NumberFormatException e) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST,
                    "Parameter 'b' is not a valid number: " + escapeHtml(paramB));
            return;
        }

        // --- Operation dispatch ---
        double result;
        String op = paramOp.trim().toLowerCase();

        // Validate operation before numeric computation
        if (!op.equals("add") && !op.equals("subtract")
                && !op.equals("multiply") && !op.equals("divide")) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST,
                    "Unknown operation '" + escapeHtml(paramOp)
                            + "'. Allowed: add, subtract, multiply, divide.");
            return;
        }

        try {
            if (op.equals("add")) {
                result = calculator.add(a, b);
            } else if (op.equals("subtract")) {
                result = calculator.subtract(a, b);
            } else if (op.equals("multiply")) {
                result = calculator.multiply(a, b);
            } else {
                // op must be "divide" here (validated above)
                result = calculator.divide(a, b);
            }
        } catch (ArithmeticException e) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
            return;
        }

        // --- Success response ---
        String formattedResult = formatResult(result);
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html lang=\"en\"><head><meta charset=\"UTF-8\">");
            out.println("<title>Calculator Result</title>");
            out.println("<style>");
            out.println("  body{font-family:sans-serif;max-width:480px;margin:60px auto;padding:0 16px;background:#f5f5f5}");
            out.println("  .card{background:#fff;border-radius:8px;padding:32px;box-shadow:0 2px 8px rgba(0,0,0,.12)}");
            out.println("  h1{margin-top:0;font-size:1.4rem;color:#333}");
            out.println("  .result{font-size:2rem;font-weight:bold;color:#1a73e8;margin:16px 0}");
            out.println("  a{color:#1a73e8;text-decoration:none}");
            out.println("</style>");
            out.println("</head><body><div class=\"card\">");
            out.println("<h1>Calculator Result</h1>");
            out.printf("<p>%s %s %s = <span class=\"result\">%s</span></p>%n",
                    escapeHtml(paramA), escapeHtml(op), escapeHtml(paramB), formattedResult);
            out.println("<p><a href=\"/calculator/index.html\">&larr; Back to Calculator</a></p>");
            out.println("</div></body></html>");
        }
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    private static boolean isNullOrBlank(String s) {
        return s == null || s.isBlank();
    }

    /**
     * Writes a minimal HTTP error page without swallowing the exception.
     */
    private static void sendError(HttpServletResponse response, int status, String message)
            throws IOException {
        response.setStatus(status);
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html lang=\"en\"><head><meta charset=\"UTF-8\">");
            out.println("<title>Error " + status + "</title>");
            out.println("<style>body{font-family:sans-serif;max-width:480px;margin:60px auto;padding:0 16px}</style>");
            out.println("</head><body>");
            out.println("<h1>Error " + status + "</h1>");
            out.println("<p>" + message + "</p>");
            out.println("<p><a href=\"/calculator/index.html\">&larr; Back</a></p>");
            out.println("</body></html>");
        }
    }

    /**
     * Formats a double result: drops the decimal part when the value is a whole number.
     */
    private static String formatResult(double value) {
        if (value == Math.floor(value) && !Double.isInfinite(value)) {
            return String.valueOf((long) value);
        }
        return String.valueOf(value);
    }

    /**
     * Minimal HTML escaping to prevent reflected XSS in error messages.
     */
    private static String escapeHtml(String input) {
        if (input == null) {
            return "";
        }
        return input
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#x27;");
    }
}

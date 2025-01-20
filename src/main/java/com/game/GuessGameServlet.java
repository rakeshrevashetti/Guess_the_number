package com.game;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.Random;

public class GuessGameServlet extends HttpServlet {
    private static final String SECRET_KEY = "secret_number";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Initialize or retrieve the secret number
        Integer secretNumber = (Integer) request.getSession().getAttribute(SECRET_KEY);
        if (secretNumber == null) {
            secretNumber = new Random().nextInt(100) + 1; // Number between 1 and 100
            request.getSession().setAttribute(SECRET_KEY, secretNumber);
        }

        String guessParam = request.getParameter("guess");
        if (guessParam != null) {
            try {
                int guess = Integer.parseInt(guessParam);
                if (guess < secretNumber) {
                    out.println("<p>Your guess is too low!</p>");
                } else if (guess > secretNumber) {
                    out.println("<p>Your guess is too high!</p>");
                } else {
                    out.println("<p>Congratulations! You guessed the number!</p>");
                    // Reset the secret number after a correct guess
                    request.getSession().removeAttribute(SECRET_KEY);
                }
            } catch (NumberFormatException e) {
                out.println("<p>Please enter a valid number.</p>");
            }
        }

        out.println("<form method='GET' action=''>");
        out.println("<label for='guess'>Enter your guess (1-100):</label>");
        out.println("<input type='text' name='guess' id='guess'/>");
        out.println("<input type='submit' value='Submit'/>");
        out.println("</form>");
    }
}

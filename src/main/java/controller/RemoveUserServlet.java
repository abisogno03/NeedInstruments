package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.UtenteDAO;

import java.io.IOException;

@WebServlet("/remove-user-servlet")
public class RemoveUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String address = "/WEB-INF/userResults/utenteRimossoOk.jsp";

        String username = request.getParameter("username");

        UtenteDAO utenteDAO = new UtenteDAO();
        utenteDAO.removeUtenteByUsername(username);

        request.setAttribute("username", username);
        request.getRequestDispatcher(address).forward(request, response);
    }
}

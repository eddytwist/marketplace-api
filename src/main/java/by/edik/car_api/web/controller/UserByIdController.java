package by.edik.car_api.web.controller;

import by.edik.car_api.service.UserService;
import by.edik.car_api.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
@WebServlet("/api/v1/users/{id}")
public class UserByIdController extends HttpServlet {

    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("id");
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writeValueAsString(userService.getById(Long.parseLong(userId)));
        out.write(jsonStr);
        out.println("<h1>UsersByID" + userId + " </h1>");
        out.flush();
        out.close();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }
}

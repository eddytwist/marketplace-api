package by.edik.car_api.web.controller;

import by.edik.car_api.model.Condition;
import by.edik.car_api.service.impl.AdServiceImpl;
import by.edik.car_api.service.impl.UserServiceImpl;
import by.edik.car_api.web.dto.CreatedAdDto;
import by.edik.car_api.web.dto.CreatedUserDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Arrays;

@WebServlet("/api/v1/user/*")
public class CreateUserController extends HttpServlet {

    private final UserServiceImpl userService = UserServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = resp.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        CreatedUserDto createdUserDto = CreatedUserDto.builder()
                .username(req.getParameter("username"))
                .password(req.getParameter("password"))
                .email(req.getParameter("email"))
                .build();
        String jsonStr = mapper.writeValueAsString(userService.create(createdUserDto));
        writer.write(jsonStr);
        writer.flush();
        writer.close();
    }
}

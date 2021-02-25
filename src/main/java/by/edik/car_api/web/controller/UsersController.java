package by.edik.car_api.web.controller;

import by.edik.car_api.service.impl.UserServiceImpl;
import by.edik.car_api.web.dto.CreatedUserDto;
import by.edik.car_api.web.dto.UpdatedUserDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static by.edik.car_api.config.ServletConstants.CHARACTER_ENCODING;
import static by.edik.car_api.config.ServletConstants.CONTENT_TYPE;

@WebServlet("/api/v1/users/")
public class UsersController extends HttpServlet {

    private final UserServiceImpl userService = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType(CONTENT_TYPE);
        resp.setCharacterEncoding(CHARACTER_ENCODING);
        PrintWriter writer = resp.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writeValueAsString(userService.getAll());
        writer.write(jsonStr);
        writer.flush();
        writer.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType(CONTENT_TYPE);
        resp.setCharacterEncoding(CHARACTER_ENCODING);
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

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType(CONTENT_TYPE);
        resp.setCharacterEncoding(CHARACTER_ENCODING);
        PrintWriter writer = resp.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        UpdatedUserDto updatedUserDto = UpdatedUserDto.builder()
                .userId(Long.parseLong(req.getParameter("userId")))
                .username(req.getParameter("username"))
                .password(req.getParameter("password"))
                .email(req.getParameter("email"))
                .build();
        String jsonStr = mapper.writeValueAsString(userService.update(updatedUserDto));
        writer.write(jsonStr);
        writer.flush();
        writer.close();
    }
}

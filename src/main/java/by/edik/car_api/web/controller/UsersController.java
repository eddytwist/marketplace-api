package by.edik.car_api.web.controller;

import by.edik.car_api.service.impl.UserServiceImpl;
import by.edik.car_api.web.dto.UserCreatedDto;
import by.edik.car_api.web.dto.UserUpdatedDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static by.edik.car_api.config.ServletConstants.APPLICATION_JSON;
import static by.edik.car_api.config.ServletConstants.UTF_8;

@WebServlet("/api/v1/users/")
public class UsersController extends HttpServlet {

    private final UserServiceImpl userService = UserServiceImpl.getInstance();
    private final Logger log = Logger.getLogger(UsersController.class);
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("GET method running.");
        resp.setContentType(APPLICATION_JSON);
        resp.setCharacterEncoding(UTF_8);
        PrintWriter writer = resp.getWriter();
        String json = mapper.writeValueAsString(userService.getAll());
        writer.write(json);
        writer.flush();
        writer.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("POST method running.");
        resp.setContentType(APPLICATION_JSON);
        resp.setCharacterEncoding(UTF_8);
        PrintWriter writer = resp.getWriter();
        UserCreatedDto userCreatedDto = UserCreatedDto.builder()
            .username(req.getParameter("username"))
            .password(req.getParameter("password"))
            .email(req.getParameter("email"))
            .build();
        log.info("Transferred params: " + req.getQueryString());
        String json = mapper.writeValueAsString(userService.create(userCreatedDto));
        writer.write(json);
        log.info("Data returned to the client: " + json);
        writer.flush();
        writer.close();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("PUT method running.");
        resp.setContentType(APPLICATION_JSON);
        resp.setCharacterEncoding(UTF_8);
        PrintWriter writer = resp.getWriter();
        UserUpdatedDto userUpdatedDto = UserUpdatedDto.builder()
            .userId(Long.parseLong(req.getParameter("userId")))
            .username(req.getParameter("username"))
            .password(req.getParameter("password"))
            .email(req.getParameter("email"))
            .build();
        log.info("Transferred params: " + req.getQueryString());
        String json = mapper.writeValueAsString(userService.update(userUpdatedDto));
        writer.write(json);
        log.info("Data returned to the client: " + json);
        writer.flush();
        writer.close();
    }
}

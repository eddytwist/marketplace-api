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

import static by.edik.car_api.config.ServletConstants.CHARACTER_ENCODING;
import static by.edik.car_api.config.ServletConstants.CONTENT_TYPE;

@WebServlet("/api/v1/users/")
public class UsersController extends HttpServlet {

    private final UserServiceImpl userService = UserServiceImpl.getInstance();
    private static final Logger LOG = Logger.getLogger(UsersController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LOG.info("GET method running.");
        resp.setContentType(CONTENT_TYPE);
        resp.setCharacterEncoding(CHARACTER_ENCODING);
        PrintWriter writer = resp.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(userService.getAll());
        writer.write(json);
        writer.flush();
        writer.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LOG.info("POST method running.");
        resp.setContentType(CONTENT_TYPE);
        resp.setCharacterEncoding(CHARACTER_ENCODING);
        PrintWriter writer = resp.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        UserCreatedDto userCreatedDto = UserCreatedDto.builder()
                .username(req.getParameter("username"))
                .password(req.getParameter("password"))
                .email(req.getParameter("email"))
                .build();
        LOG.info("Transferred params:\n" + req.getQueryString());
        String json = mapper.writeValueAsString(userService.create(userCreatedDto));
        writer.write(json);
        LOG.info("Data returned to the client:\n" + json);
        writer.flush();
        writer.close();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LOG.info("PUT method running.");
        resp.setContentType(CONTENT_TYPE);
        resp.setCharacterEncoding(CHARACTER_ENCODING);
        PrintWriter writer = resp.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        UserUpdatedDto userUpdatedDto = UserUpdatedDto.builder()
                .userId(Long.parseLong(req.getParameter("userId")))
                .username(req.getParameter("username"))
                .password(req.getParameter("password"))
                .email(req.getParameter("email"))
                .build();
        LOG.info("Transferred params:\n" + req.getQueryString());
        String json = mapper.writeValueAsString(userService.update(userUpdatedDto));
        writer.write(json);
        LOG.info("Data returned to the client:\n" + json);
        writer.flush();
        writer.close();
    }
}

package by.edik.car_api.web.controller;

import by.edik.car_api.service.UserService;
import by.edik.car_api.service.impl.UserServiceImpl;
import by.edik.car_api.web.dto.request.CreateUserRequest;
import by.edik.car_api.web.dto.request.UpdateUserRequest;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/v1/users")
public class UsersController extends BaseController {

    private final UserService userService = UserServiceImpl.getInstance();
    private final Logger log = Logger.getLogger(UsersController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        log.info("GET method running.");

        executeWithResult(resp, userService::getAll);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        log.info("POST method running.");
        log.info("Transferred params: " + req.getQueryString());

        executeWithResult(resp, () -> {
            CreateUserRequest createUserRequest = CreateUserRequest.builder()
                .username(req.getParameter("username"))
                .password(req.getParameter("password"))
                .email(req.getParameter("email"))
                .build();

            return userService.create(createUserRequest);
        });
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        log.info("PUT method running.");
        log.info("Transferred params: " + req.getQueryString());

        executeWithResult(resp, () -> {
            UpdateUserRequest updateUserRequest = UpdateUserRequest.builder()
                .userId(Long.parseLong(req.getParameter("userId")))
                .username(req.getParameter("username"))
                .password(req.getParameter("password"))
                .email(req.getParameter("email"))
                .build();

            return userService.update(updateUserRequest);
        });
    }
}

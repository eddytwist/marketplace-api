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

        executeWithStatusOk(resp, userService::getAll);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        log.info("POST method running.");
        log.info("Transferred params: " + req.getQueryString());

        executeWithStatusCreated(resp, () -> userService.create(getRequestObject(req, CreateUserRequest.class)));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        log.info("PUT method running.");
        log.info("Transferred params: " + req.getQueryString());

        executeWithStatusCreated(resp, () -> userService.update(getRequestObject(req, UpdateUserRequest.class)));
    }
}

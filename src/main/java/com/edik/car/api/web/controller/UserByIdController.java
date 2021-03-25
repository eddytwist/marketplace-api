package com.edik.car.api.web.controller;

import com.edik.car.api.service.UserService;
import com.edik.car.api.web.utils.UriUtils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@WebServlet("/api/v1/users/*")
public class UserByIdController extends BaseController {

    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        log.info("GET method running.");

        executeWithStatusOk(resp, () -> {
            Long userId = UriUtils.getId(req.getPathInfo());

            return userService.getById(userId);
        });
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        log.info("DELETE method running.");

        Long userId = UriUtils.getId(req.getPathInfo());

        userService.delete(userId);
        log.info("User id = {} successfully deleted.", userId);
    }
}

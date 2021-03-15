package by.edik.car_api.web.controller;

import by.edik.car_api.service.UserService;
import by.edik.car_api.service.impl.UserServiceImpl;
import by.edik.car_api.service.impl.UserServiceImplHiba;
import by.edik.car_api.web.utils.UriUtils;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/v1/users/*")
public class UserByIdController extends BaseController {

    private final UserService userService = UserServiceImpl.getInstance();
    private final UserServiceImplHiba userServiceHiba = UserServiceImplHiba.getInstance();
    private final Logger log = Logger.getLogger(UserByIdController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        log.info("GET method running.");

        executeWithStatusOk(resp, () -> {
            Long userId = UriUtils.getId(req.getPathInfo());

            return userServiceHiba.getById(userId);
        });
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        log.info("DELETE method running.");

        Long userId = UriUtils.getId(req.getPathInfo());

        userServiceHiba.delete(userId);
        log.info("User id = " + userId + " successfully deleted.");
    }
}

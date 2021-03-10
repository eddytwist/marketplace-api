package by.edik.car_api.web.controller;

import by.edik.car_api.service.AdService;
import by.edik.car_api.service.impl.AdServiceImpl;
import by.edik.car_api.web.utils.UriUtils;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/v1/ads/*")
public class AdByIdController extends BaseController {

    private final AdService adService = AdServiceImpl.getInstance();
    private final Logger log = Logger.getLogger(AdByIdController.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        log.info("GET method running.");

        executeWithResult(resp, () -> {
            Long adId = UriUtils.getId(req.getPathInfo());

            return adService.getFullInformationAdById(adId);
        });
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        log.info("DELETE method running.");

        Long adId = UriUtils.getId(req.getPathInfo());

        adService.delete(adId);
        log.info("Ad id = " + adId + " successfully deleted.");
    }
}

package by.edik.car_api.web.controller;

import by.edik.car_api.service.AdService;
import by.edik.car_api.service.impl.AdServiceImpl;
import by.edik.car_api.web.dto.request.CreateAdRequest;
import by.edik.car_api.web.dto.request.PatchAdRequest;
import by.edik.car_api.web.dto.request.UpdateAdRequest;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/v1/ads")
public class AdsController extends BaseController {

    public static final int DEFAULT_ADS_PER_PAGE = 10;
    private final AdService adService = AdServiceImpl.getInstance();
    private final Logger log = Logger.getLogger(AdsController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        log.info("GET method running.");
        log.info("Transferred params: " + req.getQueryString());

        executeWithStatusOk(resp, () -> {
            int page = Integer.parseInt(req.getParameter("page"));
            int size = (req.getParameter("size") == null) ? DEFAULT_ADS_PER_PAGE : Integer.parseInt(req.getParameter("size"));

            return adService.getAllShortInformationAds(page, size);
        });
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        log.info("POST method running.");
        log.info("Transferred params: " + req.getQueryString());

        executeWithStatusCreated(resp, () -> adService.create(getRequestObject(req, CreateAdRequest.class)));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        log.info("PUT method running.");
        log.info("Transferred params: " + req.getQueryString());

        executeWithStatusCreated(resp, () -> adService.update(getRequestObject(req, UpdateAdRequest.class)));
    }

    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) {
        log.info("PATCH method running.");
        log.info("Transferred params: " + req.getQueryString());

        executeWithStatusCreated(resp, () -> adService.updateAllowedFields(getRequestObject(req, PatchAdRequest.class)));
    }
}

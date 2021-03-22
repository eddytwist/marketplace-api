package com.edik.car.api.web.controller;

import com.edik.car.api.service.impl.AdServiceImpl;
import com.edik.car.api.web.utils.UriUtils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@WebServlet("/api/v1/ads/*")
public class AdByIdController extends BaseController {

    private final AdServiceImpl adService = AdServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        log.info("GET method running.");

        executeWithStatusOk(resp, () -> {
            Long adId = UriUtils.getId(req.getPathInfo());

            return adService.getFullInformationAdById(adId);
        });
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        log.info("DELETE method running.");

        Long adId = UriUtils.getId(req.getPathInfo());

        adService.delete(adId);
        log.info("Ad id = {} successfully deleted.", adId);
    }
}

package com.edik.car.api.web.controller;

import com.edik.car.api.service.AdService;
import com.edik.car.api.service.impl.AdServiceImpl;
import com.edik.car.api.web.dto.request.CreateAdRequest;
import com.edik.car.api.web.dto.request.PatchAdRequest;
import com.edik.car.api.web.dto.request.UpdateAdRequest;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@WebServlet("/api/v1/ads")
public class AdsController extends BaseController {

    public static final int DEFAULT_ADS_PER_PAGE = 10;
    public static final int DEFAULT_PAGE_NUMBER = 1;

    private final AdService adService = AdServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        log.info("GET method running.");
        log.info("Transferred params: {}", req.getQueryString());

        executeWithStatusOk(resp, () -> {
            int page = (req.getParameter("page") == null) ? DEFAULT_PAGE_NUMBER : Integer.parseInt(req.getParameter("page"));
            int size = (req.getParameter("size") == null) ? DEFAULT_ADS_PER_PAGE : Integer.parseInt(req.getParameter("size"));

            return adService.getAllShortInformationAds(page, size);
        });
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        log.info("POST method running.");
        log.info("Transferred params: {}", req.getQueryString());

        executeWithStatusCreated(resp, () -> adService.create(getRequestObject(req, CreateAdRequest.class)));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        log.info("PUT method running.");
        log.info("Transferred params: {}", req.getQueryString());

        executeWithStatusCreated(resp, () -> adService.update(getRequestObject(req, UpdateAdRequest.class)));
    }

    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) {
        log.info("PATCH method running.");
        log.info("Transferred params: {}", req.getQueryString());

        executeWithStatusCreated(resp, () -> adService.updateAllowedFields(getRequestObject(req, PatchAdRequest.class)));
    }
}

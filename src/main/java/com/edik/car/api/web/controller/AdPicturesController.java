package com.edik.car.api.web.controller;

import com.edik.car.api.service.AdService;
import com.edik.car.api.service.PictureService;
import com.edik.car.api.web.utils.UriUtils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@WebServlet("/api/v1/ads/picture/*")
public class AdPicturesController extends BaseController {

    private PictureService pictureService;
    private AdService adService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        log.info("GET method running.");

        executeWithStatusOk(resp, () -> {
            Long pictureId = UriUtils.getId(req.getPathInfo());

            return pictureService.getById(pictureId);
        });
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        log.info("DELETE method running.");

        Long pictureId = UriUtils.getId(req.getPathInfo());

        adService.deletePictureFromAdById(pictureId);
        log.info("Picture id = {} successfully deleted.", pictureId);
    }
}

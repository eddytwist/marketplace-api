package by.edik.car_api.web.controller;

import by.edik.car_api.service.AdService;
import by.edik.car_api.service.PictureService;
import by.edik.car_api.service.impl.AdServiceImpl;
import by.edik.car_api.service.impl.PictureServiceImpl;
import by.edik.car_api.web.utils.UriUtils;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/v1/ads/picture/*")
public class AdPicturesController extends BaseController {

    private final PictureService pictureService = PictureServiceImpl.getInstance();
    private final AdService adService = AdServiceImpl.getInstance();
    private final Logger log = Logger.getLogger(AdPicturesController.class);

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
        log.info("Picture id = " + pictureId + " successfully deleted.");
    }
}

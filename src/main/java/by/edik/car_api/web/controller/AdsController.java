package by.edik.car_api.web.controller;

import by.edik.car_api.dao.model.Condition;
import by.edik.car_api.service.impl.AdServiceImpl;
import by.edik.car_api.web.dto.AdCreatedDto;
import by.edik.car_api.web.dto.AdPatchedDto;
import by.edik.car_api.web.dto.AdUpdatedDto;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@WebServlet("/api/v1/ads/")
public class AdsController extends BaseController {

    private final AdServiceImpl adService = AdServiceImpl.getInstance();
    private final Logger log = Logger.getLogger(AdsController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        log.info("GET method running.");
        log.info("Transferred params: " + req.getQueryString());

        executeWithResult(resp, () -> {
            int page = Integer.parseInt(req.getParameter("page"));
            int size = Integer.parseInt(req.getParameter("size"));

            return adService.getAllShortInformationAds(page, size);
        });
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        log.info("POST method running.");
        log.info("Transferred params: " + req.getQueryString());

        executeWithResult(resp, () -> {
            AdCreatedDto adCreatedDto = AdCreatedDto.builder()
                .userId(Long.parseLong(req.getParameter("userId")))
                .year(Integer.parseInt(req.getParameter("year")))
                .brand(req.getParameter("brand"))
                .model(req.getParameter("model"))
                .engineVolume(Integer.parseInt(req.getParameter("engineVolume")))
                .condition(Condition.valueOf(req.getParameter("condition")))
                .mileage(Long.parseLong(req.getParameter("mileage")))
                .enginePower(Integer.parseInt(req.getParameter("enginePower")))
                .ownerPhoneNumbers(Arrays.asList(req.getParameterValues("ownerPhoneNumbers")))
                .pictureReferences(Arrays.asList(req.getParameterValues("pictureReferences")))
                .build();

            return adService.create(adCreatedDto);
        });
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        log.info("PUT method running.");
        log.info("Transferred params: " + req.getQueryString());

        executeWithResult(resp, () -> {
            AdUpdatedDto adUpdatedDto = AdUpdatedDto.builder()
                .adId(Long.parseLong(req.getParameter("adId")))
                .userId(Long.parseLong(req.getParameter("userId")))
                .year(Integer.parseInt(req.getParameter("year")))
                .brand(req.getParameter("brand"))
                .model(req.getParameter("model"))
                .engineVolume(Integer.parseInt(req.getParameter("engineVolume")))
                .condition(Condition.valueOf(req.getParameter("condition")))
                .mileage(Long.parseLong(req.getParameter("mileage")))
                .enginePower(Integer.parseInt(req.getParameter("enginePower")))
                .ownerName(req.getParameter("ownerName"))
                .ownerPhoneNumbers(Arrays.asList(req.getParameterValues("ownerPhoneNumbers")))
                .pictureReferences(Arrays.asList(req.getParameterValues("pictureReferences")))
                .build();

            return adService.update(adUpdatedDto);
        });
    }

    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) {
        log.info("PATCH method running.");
        log.info("Transferred params: " + req.getQueryString());

        executeWithResult(resp, () -> {
            AdPatchedDto adPatchedDto = AdPatchedDto.builder()
                .adId(Long.parseLong(req.getParameter("adId")))
                .year(Integer.parseInt(req.getParameter("year")))
                .brand(req.getParameter("brand"))
                .model(req.getParameter("model"))
                .engineVolume(Integer.parseInt(req.getParameter("engineVolume")))
                .mileage(Long.parseLong(req.getParameter("mileage")))
                .enginePower(Integer.parseInt(req.getParameter("enginePower")))
                .build();

            return adService.updateAllowedFields(adPatchedDto);
        });
    }
}

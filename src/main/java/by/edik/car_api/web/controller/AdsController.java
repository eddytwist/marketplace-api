package by.edik.car_api.web.controller;

import by.edik.car_api.dao.model.Condition;
import by.edik.car_api.service.impl.AdServiceImpl;
import by.edik.car_api.web.dto.AdCreatedDto;
import by.edik.car_api.web.dto.AdPatchedDto;
import by.edik.car_api.web.dto.AdUpdatedDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import static by.edik.car_api.config.ServletConstants.APPLICATION_JSON;
import static by.edik.car_api.config.ServletConstants.UTF_8;

@WebServlet("/api/v1/ads/")
public class AdsController extends HttpServletWithPatch {

    private final AdServiceImpl adService = AdServiceImpl.getInstance();
    private final Logger logger = Logger.getLogger(AdsController.class);
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("GET method running.");
        resp.setContentType(APPLICATION_JSON);
        resp.setCharacterEncoding(UTF_8);
        PrintWriter writer = resp.getWriter();
        mapper.findAndRegisterModules();
        int page = Integer.parseInt(req.getParameter("page"));
        int size = Integer.parseInt(req.getParameter("size"));
        logger.info("Transferred params: " + req.getQueryString());
        String json = mapper.writeValueAsString(adService.getAllShortInformationAds(page, size));
        writer.write(json);
        logger.info("Data returned to the client:" + json);
        writer.flush();
        writer.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("POST method running.");
        resp.setContentType(APPLICATION_JSON);
        resp.setCharacterEncoding(UTF_8);
        PrintWriter writer = resp.getWriter();
        mapper.findAndRegisterModules();
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
        logger.info("Transferred params: " + req.getQueryString());
        String json = mapper.writeValueAsString(adService.create(adCreatedDto));
        writer.write(json);
        logger.info("Data returned to the client: " + json);
        writer.flush();
        writer.close();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("PUT method running.");
        resp.setContentType(APPLICATION_JSON);
        resp.setCharacterEncoding(UTF_8);
        PrintWriter writer = resp.getWriter();
        mapper.findAndRegisterModules();
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
        logger.info("Transferred params: " + req.getQueryString());
        String json = mapper.writeValueAsString(adService.update(adUpdatedDto));
        writer.write(json);
        logger.info("Data returned to the client: " + json);
        writer.flush();
        writer.close();
    }

    @Override
    public void doPatch(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("PATCH method running.");
        resp.setContentType(APPLICATION_JSON);
        resp.setCharacterEncoding(UTF_8);
        PrintWriter writer = resp.getWriter();
        mapper.findAndRegisterModules();
        AdPatchedDto adPatchedDto = AdPatchedDto.builder()
            .adId(Long.parseLong(req.getParameter("adId")))
            .year(Integer.parseInt(req.getParameter("year")))
            .brand(req.getParameter("brand"))
            .model(req.getParameter("model"))
            .engineVolume(Integer.parseInt(req.getParameter("engineVolume")))
            .mileage(Long.parseLong(req.getParameter("mileage")))
            .enginePower(Integer.parseInt(req.getParameter("enginePower")))
            .build();
        logger.info("Transferred params: " + req.getQueryString());
        String json = mapper.writeValueAsString(adService.updateAllowedFields(adPatchedDto));
        writer.write(json);
        logger.info("Data returned to the client: " + json);
        writer.flush();
        writer.close();
    }
}

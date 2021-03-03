package by.edik.car_api.web.controller;

import by.edik.car_api.service.impl.AdServiceImpl;
import by.edik.car_api.web.utils.UriUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static by.edik.car_api.config.ServletConstants.APPLICATION_JSON;
import static by.edik.car_api.config.ServletConstants.UTF_8;

@WebServlet("/api/v1/ads/*")
public class AdByIdController extends HttpServlet {

    private final AdServiceImpl adService = AdServiceImpl.getInstance();
    private final Logger log = Logger.getLogger(AdByIdController.class.getName());
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("GET method running.");
        resp.setContentType(APPLICATION_JSON);
        resp.setCharacterEncoding(UTF_8);
        mapper.findAndRegisterModules();
        try (PrintWriter writer = resp.getWriter()) {
            Long adId = UriUtils.getId(req.getPathInfo());
            String json = mapper.writeValueAsString(adService.getFullInformationAdById(adId));
            log.info("Data returned to the client: " + json);
            writer.write(json);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("DELETE method running.");
        resp.setContentType(APPLICATION_JSON);
        resp.setCharacterEncoding(UTF_8);
        PrintWriter writer = resp.getWriter();
        Long adId = UriUtils.getId(req.getPathInfo());
        try {
            adService.delete(adId);
            log.info("Ad id = " + adId + " successfully deleted.");
        } catch (Exception e) {
            log.error("Ad wasn't found: " + e);
            throw new ServletException("Entity doesn't exist.");
        }
        writer.flush();
        writer.close();
    }
}

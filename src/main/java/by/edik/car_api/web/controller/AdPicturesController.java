package by.edik.car_api.web.controller;

import by.edik.car_api.service.impl.AdServiceImpl;
import by.edik.car_api.service.impl.PictureServiceImpl;
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

@WebServlet("/api/v1/ads/picture/*")
public class AdPicturesController extends HttpServlet {

    private final PictureServiceImpl pictureService = PictureServiceImpl.getInstance();
    private final AdServiceImpl adService = AdServiceImpl.getInstance();
    private final Logger logger = Logger.getLogger(AdPicturesController.class);
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("GET method running.");
        resp.setContentType(APPLICATION_JSON);
        resp.setCharacterEncoding(UTF_8);
        PrintWriter writer = resp.getWriter();
        mapper.findAndRegisterModules();
        Long pictureId = UriUtils.getId(req.getPathInfo());
        String json = mapper.writeValueAsString(pictureService.getById(pictureId));
        writer.write(json);
        logger.info("Data returned to the client: " + json);
        writer.flush();
        writer.close();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("DELETE method running.");
        resp.setContentType(APPLICATION_JSON);
        resp.setCharacterEncoding(UTF_8);
        PrintWriter writer = resp.getWriter();
        Long pictureId = UriUtils.getId(req.getPathInfo());
        try {
            adService.deletePictureFromAdById(pictureId);
            logger.info("Picture id = " + pictureId + " successfully deleted.");
        } catch (Exception e) {
            logger.error("Picture wasn't found: " + e);
            throw new ServletException("Entity doesn't exist.");
        }
        writer.flush();
        writer.close();
    }
}

package by.edik.car_api.web.controller;

import by.edik.car_api.service.impl.AdServiceImpl;
import by.edik.car_api.service.impl.PictureServiceImpl;
import by.edik.car_api.web.utils.UriUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static by.edik.car_api.config.ServletConstants.CHARACTER_ENCODING;
import static by.edik.car_api.config.ServletConstants.CONTENT_TYPE;

@WebServlet("/api/v1/ads/picture/*")
public class AdPicturesController extends HttpServlet {

    private final PictureServiceImpl pictureService = PictureServiceImpl.getInstance();
    private final AdServiceImpl adService = AdServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType(CONTENT_TYPE);
        resp.setCharacterEncoding(CHARACTER_ENCODING);
        PrintWriter writer = resp.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        Long pictureId = UriUtils.getId(req.getPathInfo());
        String jsonStr = mapper.writeValueAsString(pictureService.getById(pictureId));
        writer.write(jsonStr);
        writer.flush();
        writer.close();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(CONTENT_TYPE);
        resp.setCharacterEncoding(CHARACTER_ENCODING);
        PrintWriter writer = resp.getWriter();
        Long pictureId = UriUtils.getId(req.getPathInfo());
        try {
            adService.deletePictureFromAdById(pictureId);
            String output = "Picture id=" + pictureId + " successfully deleted.";
            writer.write(output);
        } catch (Exception e) {
            throw new ServletException();
        }
        writer.flush();
        writer.close();
    }
}

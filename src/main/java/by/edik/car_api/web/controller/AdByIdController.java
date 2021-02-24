package by.edik.car_api.web.controller;

import by.edik.car_api.service.impl.AdServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/api/v1/ads/*")
public class AdByIdController extends HttpServlet {

    private final AdServiceImpl adService = AdServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = resp.getWriter();
        ObjectMapper mapper = new ObjectMapper();

        String adId = req.getParameter("id");
        String jsonStr = mapper.writeValueAsString(adService.getById(Long.parseLong(adId)));
        writer.write(jsonStr);
        writer.flush();
        writer.close();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }
}

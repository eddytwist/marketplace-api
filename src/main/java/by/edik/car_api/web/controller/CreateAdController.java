package by.edik.car_api.web.controller;

import by.edik.car_api.model.Condition;
import by.edik.car_api.service.impl.AdServiceImpl;
import by.edik.car_api.web.dto.CreatedAdDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static by.edik.car_api.config.DateConstants.LOCAL_DATE_TIME_PATTERN_Z;
import static by.edik.car_api.config.DateConstants.TIME_ZONE;

@WebServlet("/api/v1/ad/*")
public class CreateAdController extends HttpServlet {

    private final AdServiceImpl adService = AdServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = resp.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        CreatedAdDto createdAdDto = CreatedAdDto.builder()
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
                .creationTime(LocalDateTime.now())
                .editingTime(LocalDateTime.now())
                .build();
        String jsonStr = mapper.writeValueAsString(adService.create(createdAdDto));
        writer.write(jsonStr);
        writer.flush();
        writer.close();
    }
}

package by.edik.car_api.service;

import by.edik.car_api.web.dto.request.CreatePictureRequest;
import by.edik.car_api.web.dto.request.UpdatePictureRequest;
import by.edik.car_api.web.dto.response.PictureResponse;

import java.util.List;

public interface PictureService {

    PictureResponse create(CreatePictureRequest createPictureRequest);

    PictureResponse getById(Long id);

    List<PictureResponse> getAll();

    void update(UpdatePictureRequest updatePictureRequest);

    void delete(Long id);
}

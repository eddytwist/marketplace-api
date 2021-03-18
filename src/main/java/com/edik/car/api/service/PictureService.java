package com.edik.car.api.service;

import com.edik.car.api.web.dto.request.CreatePictureRequest;
import com.edik.car.api.web.dto.request.UpdatePictureRequest;
import com.edik.car.api.web.dto.response.PictureResponse;

import java.util.List;

public interface PictureService {

    PictureResponse create(CreatePictureRequest createPictureRequest);

    PictureResponse getById(Long id);

    List<PictureResponse> getAll();

    void update(UpdatePictureRequest updatePictureRequest);

    void delete(Long id);
}

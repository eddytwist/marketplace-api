package com.edik.car.api.web.mapper;

import com.edik.car.api.dao.model.Picture;
import com.edik.car.api.web.dto.request.CreatePictureRequest;
import com.edik.car.api.web.dto.request.UpdatePictureRequest;
import com.edik.car.api.web.dto.response.PictureResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PictureMapper {

    public static PictureResponse toPictureResponse(Picture picture) {
        if (picture == null) {
            return null;
        }

        return PictureResponse.builder()
            .pictureId(picture.getPictureId())
            .reference(picture.getReference())
            .build();
    }

    public static Picture toPicture(CreatePictureRequest createPictureRequest) {
        if (createPictureRequest == null) {
            return null;
        }

        return Picture.builder()
//            .adId(createPictureRequest.getAdId())
            .reference(createPictureRequest.getReference())
            .build();
    }

    public static Picture toPicture(String pictureReference) {
        if (pictureReference == null) {
            return null;
        }

        return Picture.builder()
            .reference(pictureReference)
            .build();
    }

    public static Picture toPicture(UpdatePictureRequest updatePictureRequest) {
        if (updatePictureRequest == null) {
            return null;
        }

        return Picture.builder()
//            .adId(updatePictureRequest.getAdId())
            .pictureId(updatePictureRequest.getPictureId())
            .reference(updatePictureRequest.getReference())
            .build();
    }
}

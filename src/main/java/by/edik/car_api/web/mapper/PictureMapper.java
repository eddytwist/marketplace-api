package by.edik.car_api.web.mapper;

import by.edik.car_api.dao.model.Picture;
import by.edik.car_api.web.dto.request.CreatePictureRequest;
import by.edik.car_api.web.dto.request.UpdatePictureRequest;
import by.edik.car_api.web.dto.response.PictureResponse;
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

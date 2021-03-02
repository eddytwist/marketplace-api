package by.edik.car_api.web.mapper;

import by.edik.car_api.model.Picture;
import by.edik.car_api.web.dto.PictureCreatedDto;
import by.edik.car_api.web.dto.PictureDto;

public class PictureMapper {

    public static PictureDto pictureToPictureDto(Picture picture) {
        return PictureDto.builder()
                .pictureId(picture.getPictureId())
                .reference(picture.getReference())
                .build();
    }

    public static Picture pictureCreatedDtoToPicture(PictureCreatedDto pictureCreatedDto) {
        return Picture.builder()
                .adId(pictureCreatedDto.getAdId())
                .reference(pictureCreatedDto.getReference())
                .build();
    }
}



package by.edik.car_api.dto;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PictureDto {
    private long pictureId;
    private long adId;
    private String pictureReference;
}

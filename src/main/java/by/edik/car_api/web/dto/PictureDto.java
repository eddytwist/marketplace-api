package by.edik.car_api.web.dto;

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

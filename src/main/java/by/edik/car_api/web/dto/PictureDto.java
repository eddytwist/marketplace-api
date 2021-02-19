package by.edik.car_api.web.dto;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PictureDto {
    private Long pictureId;
    private String reference;
}

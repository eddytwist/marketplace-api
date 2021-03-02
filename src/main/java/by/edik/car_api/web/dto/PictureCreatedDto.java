package by.edik.car_api.web.dto;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PictureCreatedDto {
    private Long adId;
    private String reference;
}

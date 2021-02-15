package by.edik.car_api.model;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Picture {
    private Long pictureId;
    private Long adId;
    private String pictureReference;
}

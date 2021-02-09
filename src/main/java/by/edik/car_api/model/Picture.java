package by.edik.car_api.model;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Picture {
    private long pictureId;
    private long adId;
    private String pictureReference;
}

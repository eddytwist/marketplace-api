package by.edik.car_api.model;

import lombok.*;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInformation {
    private Long userId;
    private String name;
}

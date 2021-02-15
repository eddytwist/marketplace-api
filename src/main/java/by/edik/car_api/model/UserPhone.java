package by.edik.car_api.model;

import lombok.*;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPhone {
    private Long phoneNumberId;
    private Long userId;
    private String phoneNumber;
}

package by.edik.car_api.model;

import lombok.*;

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

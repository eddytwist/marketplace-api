package by.edik.car_api.model;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPhone {
    private long phoneNumberId;
    private long userId;
    private String phoneNumber;
}

package by.edik.car_api.model;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInformation {
    private Long userId;
    private String name;
}

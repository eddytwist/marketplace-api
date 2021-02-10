package by.edik.car_api.dto;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private long userId;
    private String username;
    private String email;
    private String password;
}

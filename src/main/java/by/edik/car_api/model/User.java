package by.edik.car_api.model;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private long userId;
    private String username;
    private String email;
    private String password;
}

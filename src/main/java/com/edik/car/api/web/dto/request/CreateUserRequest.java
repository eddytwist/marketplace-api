package com.edik.car.api.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateUserRequest {
    private String username;
    private String password;
    private String email;
    private String name;
    private List<String> userPhones;
}

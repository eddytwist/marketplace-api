package com.edik.car.api.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateUserRequest {
    private Long userId;
    private String username;
    private String password;
    private String email;
    private String name;
    private List<String> userPhones;
}

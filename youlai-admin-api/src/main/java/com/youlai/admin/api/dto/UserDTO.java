package com.youlai.admin.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserDTO {
    private Integer id;
    private String username;
    private String password;
    private Integer status;
    private String clientId;
    private List<String> roles;

}

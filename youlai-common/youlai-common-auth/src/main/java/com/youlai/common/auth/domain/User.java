package com.youlai.common.auth.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class User {
    private Integer id;
    private String username;
    private String password;
    private Integer status;
    private String clientId;
    private List<String> roles;
}

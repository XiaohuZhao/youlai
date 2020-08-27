package com.youlai.service.system.component;


import com.youlai.common.auth.constant.AuthConstant;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Component
@AllArgsConstructor
public class ResourceRoleRulesHolder {

    private RedisTemplate redisTemplate;

    @PostConstruct
    public void initResourceRolesMap(){
        Map<String, List<String>> resourceRoleMap=new TreeMap<>();
        List<String> roleNames=new ArrayList<>();
        roleNames.add("2_admin");
        resourceRoleMap.put("/youlai-service/users",roleNames);
        redisTemplate.delete(AuthConstant.RESOURCE_ROLES_MAP_KEY);
        redisTemplate.opsForHash().putAll(AuthConstant.RESOURCE_ROLES_MAP_KEY,resourceRoleMap);
    }
}

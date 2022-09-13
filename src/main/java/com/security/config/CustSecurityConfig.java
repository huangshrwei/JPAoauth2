package com.security.config;

import com.security.domain.AdminUserDetails;
import com.security.domain.UmsResource;
import com.security.service.DynamicSecurityService;
import com.security.service.UmsAdminService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * customer-security
 * 自定義配置，用於取得使用者訊息及動態權限
 */
@Configuration
@Slf4j
public class CustSecurityConfig {

    @Autowired
    private UmsAdminService adminService;

    @Bean
    public UserDetailsService userDetailsService() {
        //取得登錄者相關資訊
        return username -> {
            AdminUserDetails admin = adminService.getAdminByUsername(username);
            if (admin != null) {
            	log.info("user: " + admin.getUsername());
                return admin;
            }
            throw new UsernameNotFoundException("用户名或密码錯誤");
        };
    }

    @Bean
    public DynamicSecurityService dynamicSecurityService() {
        return new DynamicSecurityService() {
            @Override
            public Map<String, ConfigAttribute> loadDataSource() {
                Map<String, ConfigAttribute> map = new ConcurrentHashMap<>();
                List<UmsResource> resourceList = adminService.getResourceList();
                log.info("resourceList: "+resourceList.toString());
                for (UmsResource resource : resourceList) {
                    map.put(resource.getUrl(), new org.springframework.security.access.SecurityConfig(resource.getId() + ":" + resource.getName()));
                }
                return map;
            }
        };
    }

}

package com.security.service;

import com.security.domain.AdminUserDetails;
import com.security.domain.UmsResource;
import com.security.dto.UserProfileDto;
import com.security.entity.UserProfile;

import java.util.List;

/**
 * 後臺管理Service
 * Created by macro on 2020/10/15.
 */
public interface UmsAdminService {
    /**
     * 依照使用者名稱取得使用者資訊
     */
    AdminUserDetails getAdminByUsername(String username);

    /**
     * 取得權限列表
     */
    List<UmsResource> getResourceList();

    /**
     * 登錄使用者帳號密碼
     */
    String login(String username, String password);
    
    /**
     * 註冊新使用者
     */
    UserProfile register(UserProfileDto userProfileDto);    
    
    
}

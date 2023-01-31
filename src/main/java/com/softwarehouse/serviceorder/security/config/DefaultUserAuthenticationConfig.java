package com.softwarehouse.serviceorder.security.config;

import com.softwarehouse.serviceorder.security.service.UserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultUserAuthenticationConfig implements InitializingBean {
    @Autowired
    private UserService userService;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.userService.createDefaultUser();
    }
}

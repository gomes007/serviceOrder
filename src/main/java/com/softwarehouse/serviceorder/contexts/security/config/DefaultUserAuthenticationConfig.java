package com.softwarehouse.serviceorder.contexts.security.config;

import com.softwarehouse.serviceorder.contexts.security.services.UserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class DefaultUserAuthenticationConfig implements InitializingBean {
    @Autowired
    private UserService userService;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.userService.createDefaultUser();
    }
}

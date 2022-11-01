package com.softwarehouse.serviceorder.controller;

import com.softwarehouse.serviceorder.domain.ServiceOrderStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/service-order")
public class ServiceOrderController {

    @GetMapping("/possible-status")
    public Map<ServiceOrderStatus, String> getPossibleStatus() {
        var statusAndLabel = new HashMap<ServiceOrderStatus, String>();

        for (var value : ServiceOrderStatus.values()) {
            statusAndLabel.put(value, value.getLabel());
        }

        return statusAndLabel;
    }
}

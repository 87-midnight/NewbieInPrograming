package com.lcg.sample.web;

import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.rpc.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleController {

    @Autowired
    private ReferenceConfig<GenericService> referenceConfig;

    @GetMapping(value = "/getRole")
    public Object getRole(){
        referenceConfig.setInterface("com.lcg.sample.service.RoleService");
        GenericService roleService = referenceConfig.get();
        return roleService.$invoke("getRoleName",new String[]{},new Object[]{});
    }
}

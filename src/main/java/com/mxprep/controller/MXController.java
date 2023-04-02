package com.mxprep.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MXController {

    private final Logger logger = LoggerFactory.getLogger(MXController.class);

    @GetMapping("/test")
    public String getTest() {
        logger.debug("in test get");
        return "api test return";
    }

}

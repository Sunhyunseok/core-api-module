package com.sk.jdp.common.core.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
public class BaseController {
    public final Logger logger = LoggerFactory.getLogger(BaseController.class);
}

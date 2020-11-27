package com.sk.jdp.common.core.model;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Data;

@Data
public class BaseObject implements Serializable {
    public static final Logger logger = LoggerFactory.getLogger(BaseObject.class);
    private static final long serialVersionUID = 1512142315L;
}

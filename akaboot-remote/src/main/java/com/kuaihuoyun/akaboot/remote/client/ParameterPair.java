package com.kuaihuoyun.akaboot.remote.client;

import lombok.Data;

import java.io.Serializable;

@Data
public class ParameterPair implements Serializable {

    private Class<?> clazz;

    private String name;

    private Object value;

}

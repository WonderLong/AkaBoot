package com.kuaihuoyun.akaboot.remote.client;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
public class InvokeServiceParameter implements Serializable {

    private String className;

    private String method;

    private List<ParameterPair> parameterPairs;

}

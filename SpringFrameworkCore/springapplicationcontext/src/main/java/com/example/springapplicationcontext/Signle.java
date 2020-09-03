package com.example.springapplicationcontext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Signle {

    @Autowired
    private Proto proto;

    public Proto getProto() {
        return proto;
    }
}

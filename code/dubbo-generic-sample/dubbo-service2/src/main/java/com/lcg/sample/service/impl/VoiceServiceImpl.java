package com.lcg.sample.service.impl;

import com.lcg.sample.service.VoiceService;
import org.apache.dubbo.config.annotation.Service;

import java.util.Map;

@Service
public class VoiceServiceImpl implements VoiceService {

    @Override
    public String speak(Map<Object,Object> words) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<Object,Object> entry : words.entrySet()){
            builder.append(entry.getValue());
        }
        return builder.toString();
    }
}

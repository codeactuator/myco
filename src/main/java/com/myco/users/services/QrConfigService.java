package com.myco.users.services;

import com.myco.users.entities.QrConfig;
import com.myco.users.repositories.QrConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
public class QrConfigService {
    @Autowired
    private QrConfigRepository qrConfigRepository;

    public Map<String, Object> getAllConfigs() {
        List<QrConfig> allConfigs = qrConfigRepository.findAll();
        
        Map<String, Object> response = new HashMap<>();
        Map<String, QrConfig> productsMap = new HashMap<>();
        
        QrConfig defaultConfig = null;

        for (QrConfig config : allConfigs) {
            if ("default".equals(config.getReferenceId())) {
                defaultConfig = config;
            } else {
                productsMap.put(config.getReferenceId(), config);
            }
        }

        if (defaultConfig == null) {
            defaultConfig = new QrConfig();
            defaultConfig.setReferenceId("default");
            defaultConfig.setSize(128);
            defaultConfig.setFgColor("#000000");
            defaultConfig.setBgColor("#FFFFFF");
            defaultConfig.setRounded(false);
        }

        response.put("default", defaultConfig);
        response.put("products", productsMap);
        
        return response;
    }

    public void saveConfigs(Map<String, Object> payload) {
        saveOrUpdate("default", (Map<String, Object>) payload.get("default"));

        Map<String, Map<String, Object>> productsMap = (Map<String, Map<String, Object>>) payload.get("products");
        if (productsMap != null) {
            productsMap.forEach(this::saveOrUpdate);
        }
    }

    private void saveOrUpdate(String referenceId, Map<String, Object> data) {
        if (data == null) return;
        QrConfig config = qrConfigRepository.findByReferenceId(referenceId).orElse(new QrConfig());
        config.setReferenceId(referenceId);
        if (data.containsKey("size")) config.setSize(Integer.parseInt(data.get("size").toString()));
        if (data.containsKey("fgColor")) config.setFgColor((String) data.get("fgColor"));
        if (data.containsKey("bgColor")) config.setBgColor((String) data.get("bgColor"));
        if (data.containsKey("rounded")) config.setRounded((Boolean) data.get("rounded"));
        qrConfigRepository.save(config);
    }
}
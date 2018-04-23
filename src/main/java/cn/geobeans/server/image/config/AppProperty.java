package cn.geobeans.server.image.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppProperty {

    @Value("${app.version}")
    private String version;
    @Value("${app.store.path}")
    private String storePath;


    public String getStorePath() {
        return storePath;
    }

    public String getVersion() {
        return version;
    }
}

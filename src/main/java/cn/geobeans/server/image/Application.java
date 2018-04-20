package cn.geobeans.server.image;

import cn.geobeans.server.image.config.AppProperty;
import cn.geobeans.server.image.image.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {


        ConfigurableApplicationContext context =  SpringApplication.run(Application.class,args);

        AppProperty property = context.getBean(AppProperty.class);
        ImageService imageService = context.getBean(ImageService.class);

        StringBuilder sb = new StringBuilder("\n############## Image Server Starting ####################");

        sb.append("\n#  store.path : " + property.getStorePath() );
        sb.append("\n#  current.count : " + imageService.countAll() );

        sb.append("\n############## Image Server Started #####################");
        log.info(sb.toString());

    }
}

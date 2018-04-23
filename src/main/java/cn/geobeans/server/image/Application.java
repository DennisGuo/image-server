package cn.geobeans.server.image;

import cn.geobeans.server.image.config.AppProperty;
import cn.geobeans.server.image.image.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {


        ConfigurableApplicationContext context =  SpringApplication.run(Application.class,args);

        AppProperty property = context.getBean(AppProperty.class);
        ImageService imageService = context.getBean(ImageService.class);

        StringBuilder sb = new StringBuilder("\n############## Image Server ["+property.getVersion()+"] Starting ####################");

        sb.append("\n#  store.path \t\t: " + property.getStorePath() );
        sb.append("\n#  current.count \t: " + imageService.countAll() );

        sb.append("\n############## Image Server ["+property.getVersion()+"] Started #####################");
        log.info(sb.toString());

    }

}

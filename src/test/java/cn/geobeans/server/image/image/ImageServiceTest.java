package cn.geobeans.server.image.image;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ImageServiceTest {

    @Autowired
    ImageService service;

    @Test
    public void save() {
        Image img = new Image();
        img.setPath("/201804/xxx.jpg");
        img.setPathThumbnail("/201804/xxx_thumb.jpg");
        img.setContent("{\"name\":\"image server\"}");

        Image rs = service.save(img);

        System.out.println(rs);

    }
}

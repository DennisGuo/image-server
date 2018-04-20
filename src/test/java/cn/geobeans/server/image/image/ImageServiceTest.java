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
        img.setSource("测绘局");
        img.setLevel("零级产品");
        img.setPath("/201804/xxx.jpg");
        img.setCoordinate(new double[]{118.00018387498755,38.99993870833748,119.00018796109839,39.999942794448316});

        Image rs = service.save(img);

        System.out.println(rs);

    }
}

package cn.geobeans.server.image.image;

import cn.geobeans.server.image.common.ImageUtil;
import cn.geobeans.server.image.common.JsonResponse;
import cn.geobeans.server.image.config.AppProperty;
import cn.geobeans.server.image.store.StorageService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@RestController
@RequestMapping("/image")
public class ImageController {


    @Autowired
    ImageService service;

    @Autowired
    StorageService storageService;

    private static final String POST = "";
    private static final String POST_UPLOAD = "/upload";
    private static final String GET = "";

    @RequestMapping(path = GET,method = RequestMethod.GET)
    public JsonResponse get(){
        Iterable<Image> rs = service.getAll();
        return new JsonResponse(rs);
    }


    @RequestMapping(path = POST_UPLOAD,method = RequestMethod.POST)
    public JsonResponse update(@RequestParam(value = "width",required = false,defaultValue = "256") Integer width,
                               @RequestParam("file") MultipartFile file){

        try {

            String path = storageService.store(file);
            String thumb = ImageUtil.thumbnailImage(storageService.getRootLocation() +  path,width);
            Map<String,String> rs = new HashMap<>();
            rs.put("path",path);
            rs.put("pathThumbnail",thumb);
            return new JsonResponse(rs);

        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResponse(e.getMessage());
        }

    }

    /**
     *
     * @param body
     * @return
     */
    @RequestMapping(path = POST,method = RequestMethod.POST)
    public JsonResponse save(@RequestBody JSONObject body){

        try {
            Image img = new Image();
            img.setSource(body.getString("source"));
            img.setLevel(body.getString("level"));
            img.setPath(body.getString("path"));
            img.setPathThumbnail(body.getString("pathThumbnail"));

            JSONArray coord = body.getJSONArray("coordinate");
            double[] coordinate = new double[coord.size()];
            for (int i = 0; i < coord.size(); i++) {
                coordinate[i] = coord.getDouble(i);
            }
            img.setCoordinate(coordinate);

            img = service.save(img);

            return new JsonResponse(img);
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResponse(e.getMessage());
        }

    }


}

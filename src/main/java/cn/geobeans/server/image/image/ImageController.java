package cn.geobeans.server.image.image;

import cn.geobeans.server.image.common.ImageUtil;
import cn.geobeans.server.image.common.JsonResponse;
import cn.geobeans.server.image.config.AppProperty;
import cn.geobeans.server.image.store.StorageService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Cleanup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@RestController
@RequestMapping("/image")
public class ImageController {

    private  final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    ImageService service;

    @Autowired
    StorageService storageService;

    private static final String POST = "";
    private static final String POST_UPLOAD = "/upload";
    private static final String GET = "";
    private static final String GET_IMAGE = "/{dir}/{name}";

    @RequestMapping(path = GET_IMAGE,method = RequestMethod.GET)
    public void getImage(@PathVariable String dir,@PathVariable String name, HttpServletResponse response){
        String path = "/"+dir + "/" + name;
        log.info("get image form path: "+path);
        File file = storageService.getByImagePath(path);
        try {
            response.setCharacterEncoding("UTF-8");
            if(!file.exists()){
                response.setContentType("application/json");
                @Cleanup PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(new JsonResponse("文件不存在")));
            }else{
                String type = Files.probeContentType(file.toPath());
                if(type != null){
                    response.setContentType(type);
                }else{
                    response.setContentType("application/octet-stream");
                }
                @Cleanup OutputStream out = response.getOutputStream();
                @Cleanup FileInputStream fis = new FileInputStream(file);
                byte[] buff = new byte[1024];
                int n;
                while((n = fis.read(buff) ) != -1){
                    out.write(buff,0,n);
                }
            }
            response.flushBuffer();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(path = GET,method = RequestMethod.GET)
    public JsonResponse get(Pageable page){
        Page<Image> rs = service.getPage(page);
        Map<String,Object> map = new HashMap<>();
        map.put("rows",rs.getContent());
        map.put("total",rs.getTotalElements());
        map.put("totalPage",rs.getTotalPages());

        return new JsonResponse(map);
    }


    @RequestMapping(path = POST_UPLOAD,method = RequestMethod.POST)
    public JsonResponse update(@RequestParam(value = "width",required = false,defaultValue = "256") Integer width,
                               @RequestParam("file") MultipartFile file){

        try {
            String rootPath = storageService.getRootLocation();
            String path = storageService.store(file);
            String thumb = ImageUtil.thumbnailImage(rootPath +  path,width);
            Map<String,String> rs = new HashMap<>();
            rs.put("path",path);
            rs.put("pathThumbnail",thumb.replace(rootPath ,""));
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
            String keyPath = "path" , keyPathThumb = "pathThumbnail";
            img.setPath(body.getString(keyPath));
            img.setPathThumbnail(body.getString(keyPathThumb));

            img.setContent(body.getJSONObject("content").toJSONString());

            img = service.save(img);

            return new JsonResponse(img);
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResponse(e.getMessage());
        }

    }


}

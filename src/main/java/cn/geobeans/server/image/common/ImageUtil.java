package cn.geobeans.server.image.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class ImageUtil {

    private static final Logger log = LoggerFactory.getLogger(ImageUtil.class);

    private static String DEFAULT_SUFFIX = "_thumb";
    private static Boolean DEFAULT_FORCE = false;//建议该值为false


    public static String thumbnailImage(String imagePath, int w) {
        return thumbnailImage(imagePath,w,w, DEFAULT_SUFFIX,DEFAULT_FORCE);
    }
    /**
     * <p>Title: thumbnailImage</p>
     * <p>Description: 依据图片路径生成缩略图 </p>
     * @param imagePath    原图片路径
     * @param w            缩略图宽
     * @param h            缩略图高
     * @param thumbSuffix  生成缩略图的名称后缀
     * @param force        是否强制依照宽高生成缩略图(假设为false，则生成最佳比例缩略图)
     */
    public static String thumbnailImage(String imagePath, int w, int h, String thumbSuffix, boolean force){
        File imgFile = new File(imagePath);
        if(imgFile.exists()){
            try {
                // ImageIO 支持的图片类型 : [BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG, JPEG, WBMP, GIF, gif]
                String types = Arrays.toString(ImageIO.getReaderFormatNames());
                String suffix = null;
                // 获取图片后缀
                if(imgFile.getName().indexOf(".") > -1) {
                    suffix = imgFile.getName().substring(imgFile.getName().lastIndexOf(".") + 1);
                }// 类型和图片后缀所有小写，然后推断后缀是否合法
                if(suffix == null || types.toLowerCase().indexOf(suffix.toLowerCase()) < 0){
                    throw new RuntimeException("Sorry, the image suffix is illegal. the standard image suffix is {}." + types);
                }
                log.debug("target image's size, width:{}, height:{}.",w,h);
                Image img = ImageIO.read(imgFile);
                if(!force){
                    // 依据原图与要求的缩略图比例，找到最合适的缩略图比例
                    int width = img.getWidth(null);
                    int height = img.getHeight(null);
                    if((width*1.0)/w < (height*1.0)/h){
                        if(width > w){
                            h = Integer.parseInt(new java.text.DecimalFormat("0").format(height * w/(width*1.0)));
                            log.debug("change image's height, width:{}, height:{}.",w,h);
                        }
                    } else {
                        if(height > h){
                            w = Integer.parseInt(new java.text.DecimalFormat("0").format(width * h/(height*1.0)));
                            log.debug("change image's width, width:{}, height:{}.",w,h);
                        }
                    }
                }
                BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
                Graphics g = bi.getGraphics();
                g.drawImage(img, 0, 0, w, h, Color.LIGHT_GRAY, null);
                g.dispose();
                String p = imgFile.getPath();
                // 将图片保存在原文件夹并加上后缀
                String filename = imgFile.getName();
                filename = filename.replace("."+suffix,thumbSuffix+"."+suffix);
                String path = p.substring(0,p.lastIndexOf(File.separator)) + File.separator +filename;
                ImageIO.write(bi, suffix, new File(path));

                log.debug("缩略图在原路径下生成成功："+path);
                return path;
            } catch (IOException e) {
                throw new RuntimeException("generate thumbnail image failed : "+e.getMessage()+".");
            }
        }else{
            throw new RuntimeException("the image["+imagePath+"] is not exist.");
        }
    }

    public static void main(String[] args) {
        new ImageUtil().thumbnailImage("/home/ghx/workspace/java/image-server/data/p1.jpg", 250, 250, DEFAULT_SUFFIX,DEFAULT_FORCE);
    }
}
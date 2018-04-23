package cn.geobeans.server.image.image;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@JsonIgnoreProperties(value = {

})
public class Image {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;
    String path;
    String pathThumbnail;

    @Column(columnDefinition = "CLOB")
    String content;

//    String source;
//    String level;
//
//    //左下
//    Double xx;
//    Double xy;
//
//    //右上
//    Double yx;
//    Double yy;

    @CreationTimestamp
    Date createTime;

    @Transient
    public JSONObject getContent() {
        if (this.content != null){
            return JSON.parseObject(this.content);

        }
        return null;
    }
}

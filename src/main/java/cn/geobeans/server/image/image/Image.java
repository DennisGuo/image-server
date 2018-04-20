package cn.geobeans.server.image.image;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@JsonIgnoreProperties(value = {
        "xx","xy","yx","yy"
})
public class Image {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;
    String path;
    String pathThumbnail;
    String source;
    String level;

    //左下
    Double xx;
    Double xy;

    //右上
    Double yx;
    Double yy;

    @CreationTimestamp
    Date createTime;

    public void setCoordinate(double[] arr){
        this.xx = arr[0];
        this.xy = arr[1];
        this.yx = arr[2];
        this.yy = arr[3];
    }

    @Transient
    public double[] getCoordinate(){
        return new double[]{this.xx,this.xy,this.yx,this.yy};
    }
}

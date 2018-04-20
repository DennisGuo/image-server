package cn.geobeans.server.image.common;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.text.DecimalFormat;
import java.util.Calendar;

@RunWith(JUnit4.class)
public class MainTest {

    @Test
    public void date(){
        String[] arr = "p1.pxx-23.xswde.jpg".split("\\.");
        String type = arr[arr.length-1];
        System.out.println(type);
        Calendar cal = Calendar.getInstance();
        String date = cal.get(Calendar.YEAR) + "" +new DecimalFormat("00").format(cal.get(Calendar.MONTH)+1)+ "" + new DecimalFormat("00").format(cal.get(Calendar.DATE));
        System.out.println(date);
    }

    @Test
    public void replace(){
        System.out.println("sxsdwe2w3e.jpg".replace(".jpg","_thumb"+".jpg"));
    }
}

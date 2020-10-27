package test;

import com.atguigu.springcloud.GatewayMain9527;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.time.ZonedDateTime;

@SpringBootTest
@ContextConfiguration(classes = GatewayMain9527.class)
public class TestTime {


    public static void main(String[] args) {
        ZonedDateTime now = ZonedDateTime.now();
        System.out.println(now);
    }


}

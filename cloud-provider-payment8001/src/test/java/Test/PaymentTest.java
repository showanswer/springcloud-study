package Test;


import com.atguigu.springcloud.PaymentMain8001;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;

@SpringBootTest
@ContextConfiguration(classes = PaymentMain8001.class)
public class PaymentTest {

    @Resource
    private PaymentService paymentService;

    @Test
    public void run(){
        Payment payment = paymentService.getPaymentById((long) 1);
        System.out.println(payment);
    }


}

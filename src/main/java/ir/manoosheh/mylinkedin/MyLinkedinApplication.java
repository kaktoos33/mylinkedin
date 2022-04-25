package ir.manoosheh.mylinkedin;

import com.cloudinary.Cloudinary;
import com.cloudinary.SingletonManager;
import com.cloudinary.utils.ObjectUtils;
import ir.manoosheh.mylinkedin.configuration.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class MyLinkedinApplication {

    public static void main(String[] args) {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dut18eswa",
                "api_key", "829474446521885",
                "api_secret", "K6Jc5z8d4pQt2v1uxAaa4FUb-Vw"));
        SingletonManager manager = new SingletonManager();
        manager.setCloudinary(cloudinary);
        manager.init();
        SpringApplication.run(MyLinkedinApplication.class, args);
    }

}

import com.rogchen.www.mybatis.MybatisDemoApplication;
import com.rogchen.www.mybatis.service.MybatisDemoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Product: IntelliJ IDEA
 * @Author Rogchen chenhk128@163.com
 * @Created Date: 2018/3/20 13:33
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MybatisDemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
public class runTest {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private MybatisDemoService mybatisDemoService;

    @Test
    public void get() throws Exception {
        Map<String,String> multiValueMap = new HashMap<>();
        multiValueMap.put("username","lake");//传值，但要在url上配置相应的参数
        Map result = testRestTemplate.getForObject("/oracle/getMap?username={username}",Map.class);
        System.out.println(result);
    }
}

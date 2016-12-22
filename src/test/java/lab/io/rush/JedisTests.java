package lab.io.rush;

import lab.io.rush.model.User;
import lab.io.rush.util.JedisAdapter;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * redis层单元测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DemoApplication.class)
@Sql("/init-schema.sql")
public class JedisTests {
    @Autowired
    JedisAdapter jedisAdapter;

    /**
     * 缓存层的接口测试
     */
    @Test
    public void intiData(){
        User user=new User();
        user.setPassword("pwd");
        user.setName("user1");
        user.setSalt("salt");
        user.setUser_open_id("");
        jedisAdapter.setObject("user1xx",user);

        User u=jedisAdapter.getObject("user1xx",User.class);
        System.out.println(ToStringBuilder.reflectionToString(u));
    }
}

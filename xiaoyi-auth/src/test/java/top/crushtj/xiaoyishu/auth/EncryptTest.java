package top.crushtj.xiaoyishu.auth;

import com.alibaba.druid.filter.config.ConfigTools;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author ayi
 * @version V1.0
 * @title DruidTest
 * @description
 * @date 2025/12/01
 */

@SpringBootTest
@Slf4j
public class EncryptTest {
    //@Autowired
    //private StringEncryptor defaultLazyEncryptor;
    //private StringEncryptor pooledPbeStringEncryptor;

    /**
     * Druid 密码加密
     */
    @Test
    @SneakyThrows
    void testEncodePassword() {
        // 你的密码
        String password = "HhpxE2HWE4bGTyB5";
        String[] arr = ConfigTools.genKeyPair(512);

        // 私钥
        log.info("privateKey: {}", arr[0]);
        // 公钥
        log.info("publicKey: {}", arr[1]);

        // 通过私钥加密密码
        String encodePassword = ConfigTools.encrypt(arr[0], password);
        log.info("password: {}", encodePassword);
    }

    @Test
    void smsEncode() {
        String accessKeyId = manualEncrypt("", "Yu020320.");
        System.out.println("accessKeyId：" + accessKeyId);
        String accessKeySecret = manualEncrypt("", "Yu020320.");
        System.out.println("accessKeySecret：" + accessKeySecret);
    }

    private String manualEncrypt(String plainText, String secretKey) {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(secretKey); // 密钥
        config.setAlgorithm("PBEWithMD5AndDES"); // 算法
        config.setPoolSize(1); // 池大小（默认）
        encryptor.setConfig(config);
        return encryptor.encrypt(plainText);
    }
}

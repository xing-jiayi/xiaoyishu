package top.crushtj.xiaoyishu.auth;

import com.alibaba.druid.filter.config.ConfigTools;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.iv.RandomIvGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

/**
 * @author ayi
 * @version V1.0
 * @title DruidTest
 * @description
 * @date 2025/12/01
 */

@SpringBootTest
@Slf4j
@TestPropertySource(properties = {"jasypt.encryptor.password=GhaU7VjZd2b3M4Hbx4SelEXZc"})
public class EncryptTest {
    //@Autowired
    //private StringEncryptor defaultLazyEncryptor;
    //private StringEncryptor pooledPbeStringEncryptor;
    @Value("${jasypt.encryptor.password}")
    private String encryptorPassword;
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
    void encrypt() {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();

        // JDK 17适配的核心配置（关键参数必须完整）
        encryptor.setPassword(encryptorPassword); // AES-256要求密钥至少32位
        encryptor.setAlgorithm("PBEWithHMACSHA512AndAES_256");       // JDK17原生支持的算法
        encryptor.setKeyObtentionIterations(1000);                  // 迭代次数（固定值）
        encryptor.setStringOutputType("base64");                    // 输出格式（固定）
        encryptor.setProviderName("SunJCE");                        // 加密提供者（JDK17默认）
        encryptor.setIvGenerator(new RandomIvGenerator());          // AES必须的IV生成器

        // 待加密的原始值
        String accessKeyId = "ayi";
        String accessKeySecret = "HhpxE2HWE4bGTyB5";

        try {
            String cipherAccessKeyId = encryptor.encrypt(accessKeyId);
            System.out.println("accessKeyId加密成功，密文：" + cipherAccessKeyId);

            String decryptAccessKeyId = encryptor.decrypt(cipherAccessKeyId);
            System.out.println("accessKeyId解密成功，明文：" + decryptAccessKeyId);

            String cipherAccessKeySecret = encryptor.encrypt(accessKeySecret);
            System.out.println("accessKeySecret加密成功，密文：" + cipherAccessKeySecret);

            String decryptAccessKeySecret = encryptor.decrypt(cipherAccessKeySecret);
            System.out.println("accessKeySecret解密成功，明文：" + decryptAccessKeySecret);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("加密失败原因：" + e.getMessage());
        }
    }

}

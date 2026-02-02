package top.crushtj.xiaoyi.auth;

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
@TestPropertySource(properties = {"jasypt.encryptor.password="})
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
        String password = "";//密码
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
        String password = ""; // 加密密码
        encryptor.setPassword(password); // AES-256要求密钥至少32位
        encryptor.setAlgorithm("PBEWithHMACSHA512AndAES_256");       // JDK17原生支持的算法
        encryptor.setKeyObtentionIterations(1000);                  // 迭代次数（固定值）
        encryptor.setStringOutputType("base64");                    // 输出格式（固定）
        encryptor.setProviderName("SunJCE");                        // 加密提供者（JDK17默认）
        encryptor.setIvGenerator(new RandomIvGenerator());          // AES必须的IV生成器

        // 待加密的原始值
        String encry1 = ""; // 待加密的原始值1
        String encry2 = ""; // 待加密的原始值2
        String encry3 = ""; // 待加密的原始值3

        try {
            String cipherText1 = encryptor.encrypt(encry1);
            System.out.println("字段1加密成功，密文：" + cipherText1);
            String cipherText2 = encryptor.encrypt(encry2);
            System.out.println("字段2加密成功，密文：" + cipherText2);
            String cipherText3 = encryptor.encrypt(encry3);
            System.out.println("字段2加密成功，密文：" + cipherText3);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("加密失败原因：" + e.getMessage());
        }
    }

    @Test
    public void dencrypt(){
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        String cipherText1 = "";//密文
        String cipherText2 = "";//密文
        String cipherText3 = "";//密文
        String password = "";//加密密码
        encryptor.setPassword(password); // AES-256要求密钥至少32位
        encryptor.setAlgorithm("PBEWithHMACSHA512AndAES_256");       // JDK17原生支持的算法
        encryptor.setKeyObtentionIterations(1000);                  // 迭代次数（固定值）
        encryptor.setStringOutputType("base64");                    // 输出格式（固定）
        encryptor.setProviderName("SunJCE");                        // 加密提供者（JDK17默认）
        encryptor.setIvGenerator(new RandomIvGenerator());
        try {
            String text1 = encryptor.decrypt(cipherText1);
            System.out.println("字段1解密成功，明文：" + text1);
            String text2 = encryptor.decrypt(cipherText2);
            System.out.println("字段2解密成功，明文：" + text2);
            String text3 = encryptor.decrypt(cipherText3);
            System.out.println("字段2解密成功，明文：" + text3);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("加密失败原因：" + e.getMessage());
        }// AES必须的IV生成器
    }


}

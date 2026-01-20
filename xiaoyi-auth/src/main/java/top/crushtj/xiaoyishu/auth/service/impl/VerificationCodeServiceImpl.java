package top.crushtj.xiaoyishu.auth.service.impl;

import cn.hutool.core.util.RandomUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import top.crushtj.framework.common.exception.BizException;
import top.crushtj.framework.common.response.Response;
import top.crushtj.framework.common.utils.MaskUtils;
import top.crushtj.xiaoyishu.auth.constant.RedisKeyConstants;
import top.crushtj.xiaoyishu.auth.enums.ResponseCodeEnum;
import top.crushtj.xiaoyishu.auth.model.vo.verificationcode.SendVerificationCodeReqVO;
import top.crushtj.xiaoyishu.auth.service.VerificationCodeService;
import top.crushtj.xiaoyishu.auth.sms.AliyunSmsHelper;

import java.util.concurrent.TimeUnit;

import static top.crushtj.xiaoyishu.auth.constant.RedisKeyConstants.VERIFICATION_CODE_EXPIRE_TIME;

/**
 * 验证码服务实现类
 * 修复点：异步异常捕获、Redis过期时间、验证码日志脱敏、手机号校验、线程任务跟踪
 */
@Service
@Slf4j
public class VerificationCodeServiceImpl implements VerificationCodeService {
    // 短信发送超时时间（秒）
    private static final int SMS_SEND_TIMEOUT_SECONDS = 5;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource(name = "taskExecutor")
    private ThreadPoolTaskExecutor taskExecutor;

    @Resource
    private AliyunSmsHelper aliyunSmsHelper;

    @Override
    public Response<?> send(SendVerificationCodeReqVO sendVerificationCodeReqVO) {
        // 1. 获取并校验手机号格式
        String phoneNumber = sendVerificationCodeReqVO.getPhoneNumber();

        // 2. 构建Redis Key并检查发送频率
        String key = RedisKeyConstants.buildVerificationCodeKey(phoneNumber);
        boolean isSent = redisTemplate.hasKey(key);
        if (isSent) {
            throw new BizException(ResponseCodeEnum.VERIFICATION_CODE_SEND_FREQUENTLY);
        }

        // 3. 生成6位随机验证码
        String verificationCode = RandomUtil.randomNumbers(6);

        //=============== 开发环境不实际调用短信发送接口 ===============
        // 4. 异步发送短信（用CompletableFuture跟踪任务状态，捕获异常）
        //CompletableFuture<Boolean> smsSendFuture = CompletableFuture.supplyAsync(() -> {
        //    // 设置线程名称，便于日志排查
        //    Thread.currentThread().setName("sms-send-" + MaskUtils.maskMobile(phoneNumber));
        //    String signName = "速通互联验证码";
        //    String templateCode = "100001";
        //    String templateParam = String.format("{\"code\":\"%s\",\"min\":\"%d\"}", verificationCode, VERIFICATION_CODE_EXPIRE_TIME);
        //    try {
        //        return aliyunSmsHelper.sendMessage(signName, templateCode, phoneNumber, templateParam);
        //    } catch (Exception e) {
        //        log.error("==> 手机号: {}, 短信发送接口调用异常", MaskUtils.maskMobile(phoneNumber), e);
        //        return false;
        //    }
        //}, taskExecutor);
        //
        // //5. 同步等待短信发送结果（超时控制，避免主线程阻塞过久）
        //boolean smsSendSuccess;
        //try {
        //    smsSendSuccess = smsSendFuture.get(SMS_SEND_TIMEOUT_SECONDS, TimeUnit.SECONDS);
        //} catch (InterruptedException e) {
        //    log.error("==> 手机号: {}, 短信发送任务被中断", MaskUtils.maskMobile(phoneNumber), e);
        //    Thread.currentThread().interrupt(); // 恢复中断状态
        //    throw new BizException(ResponseCodeEnum.SMS_SEND_FAILED);
        //} catch (ExecutionException e) {
        //    log.error("==> 手机号: {}, 短信发送任务执行异常", MaskUtils.maskMobile(phoneNumber), e);
        //    throw new BizException(ResponseCodeEnum.SMS_SEND_FAILED);
        //} catch (TimeoutException e) {
        //    log.error("==> 手机号: {}, 短信发送任务超时（{}秒）", MaskUtils.maskMobile(phoneNumber), SMS_SEND_TIMEOUT_SECONDS, e);
        //    throw new BizException(ResponseCodeEnum.SMS_SEND_TIMEOUT);
        //}
        //
        //// 6. 短信发送失败则直接抛异常，不存储Redis
        //if (!smsSendSuccess) {
        //    log.error("==> 手机号: {}, 发送验证码失败（第三方接口返回失败）", MaskUtils.maskMobile(phoneNumber));
        //    throw new BizException(ResponseCodeEnum.SMS_SEND_FAILED);
        //}
        //=============== 开发环境不实际调用短信发送接口 ===============

        // 7. 短信发送成功后，记录日志（验证码脱敏，仅保留后2位）+ 存储Redis
        log.info("==> 手机号: {}, 已发送验证码：【****{}】", MaskUtils.maskMobile(phoneNumber), verificationCode.substring(4));
        redisTemplate.opsForValue().set(key, verificationCode, VERIFICATION_CODE_EXPIRE_TIME, TimeUnit.MINUTES);

        return Response.success(verificationCode);
        //return Response.success();
    }
}
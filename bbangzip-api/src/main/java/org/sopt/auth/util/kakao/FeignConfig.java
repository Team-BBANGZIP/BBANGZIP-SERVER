package org.sopt.auth.util.kakao;

import org.sopt.BbangzipApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients(basePackageClasses = BbangzipApplication.class)
@Configuration
public class FeignConfig {
}

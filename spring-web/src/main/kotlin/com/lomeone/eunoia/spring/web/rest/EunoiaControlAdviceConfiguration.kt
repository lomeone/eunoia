package com.lomeone.eunoia.spring.web.rest

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class EunoiaControlAdviceConfiguration {
    @Bean
    open fun eunoiaExceptionControlAdvice(): EunoiaExceptionControlAdvice = EunoiaExceptionControlAdvice()
}

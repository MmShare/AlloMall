package com.example.allomall.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

/**
 * 用户: MRDOUBI
 * 日期: 2018/10/15 12:32
 * 内容:
 */
@Configuration
public class ThymeleafConfig {
    private final ThymeleafProperties properties;

    @Value("${spring.thymeleaf.templates_root:}")
    private String templatesRoot;

    @Autowired
    public ThymeleafConfig(ThymeleafProperties properties) {
        this.properties = properties;
    }

    /**
     * user: MRDOUBI
     * 邮箱：1327466228@qq.com
     * 功能描述：动态加载html
     * 日期:下午 12:37 2018/10/15
     * @return resolver
     */
    @ConditionalOnProperty(
            value = {"spring.thymeleaf.develop"},
            havingValue = "false",matchIfMissing = false)
    @Bean
    public ITemplateResolver defaultTemplateResolver() {
        FileTemplateResolver resolver = new FileTemplateResolver();
        resolver.setSuffix(properties.getSuffix());
        resolver.setPrefix(templatesRoot);
        resolver.setTemplateMode(properties.getMode());
        resolver.setCacheable(properties.isCache());
        return resolver;
    }
}

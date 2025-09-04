package com.tj.id.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * @Author: zay
 * @Date: 2023-08-29 14:48
 */
@Data
@Validated
@Component
@ConfigurationProperties(prefix = "id")
public class IdProperties {

    private Integer length;
}

package org.cat73.bukkitboot.annotation.core.condition;

import org.cat73.bukkitboot.util.reflect.NMS;

import java.lang.annotation.*;

/**
 * 当正在运行的服务器符合特定 NMS 版本时才将类注册为 Bean
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ConditionalOnNMSVersion {
    /**
     * 需要的 NMS 版本列表
     * @return NMS 版本列表
     */
    NMS[] value();
}

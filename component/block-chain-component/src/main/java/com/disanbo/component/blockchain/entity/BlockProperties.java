package com.disanbo.component.blockchain.entity;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author wangtao
 * @date 2018/11/6 10:32
 */
@Setter
@Component
@ConfigurationProperties("block.chain")
public class BlockProperties {
    private static int counter = 0;
    private String execer;
    private Set<String> urls;

    public String getExecer() {
        return execer;
    }

    public String getUrls() {
        int i = urls.size();
        if (counter >= i) {
            counter = 0;
        }
        String url = urls.stream().sorted().skip(counter).findFirst().orElse(null);
        counter++;
        return url;
    }
}

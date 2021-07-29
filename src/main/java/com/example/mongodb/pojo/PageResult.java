package com.example.mongodb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * @author Janwes
 * @version 1.0
 * @package com.example.domain.vo
 * @date 2021/2/16 21:58
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> implements Serializable {
    // 总记录数
    private Long counts;
    // 页大小
    private Long pageSize;
    // 总页数
    private Long pages;
    // 当前页码
    private Long page;
    // 黑名单用户信息列表
    private List<T> items = Collections.emptyList();

    public PageResult(Long counts, Long pageSize, Long page, List<T> items) {
        this.counts = counts;
        this.pageSize = pageSize;
        this.pages = counts % pageSize == 0 ? counts / pageSize : counts / pageSize + 1;
        this.page = page;
        this.items = items;
    }
}

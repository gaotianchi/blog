package com.gaotianchi.resource.web.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class PageInfo<T> {
    private List<T> items;   // 泛型 T 代表列表中存储的对象类型
    private Integer totalPage;
    private Integer currentPage;
}

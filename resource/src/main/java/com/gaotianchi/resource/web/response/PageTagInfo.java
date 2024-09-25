package com.gaotianchi.resource.web.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PageTagInfo {
    private List<TagInfo> tagInfoList;
    private Integer totalPage;
    private Integer currentPage;
}

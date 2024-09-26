package com.gaotianchi.resource.web.response.page;

import com.gaotianchi.resource.web.response.info.TagInfo;
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

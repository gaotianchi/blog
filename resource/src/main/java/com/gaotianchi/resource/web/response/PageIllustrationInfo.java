package com.gaotianchi.resource.web.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class PageIllustrationInfo {
    private List<IllustrationInfo> illustrationInfoList;
    private Integer totalPage;
    private Integer currentPage;
}

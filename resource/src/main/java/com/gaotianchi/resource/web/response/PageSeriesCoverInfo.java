package com.gaotianchi.resource.web.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class PageSeriesCoverInfo {
    private List<SeriesCoverInfo> seriesCoverInfoList;
    private Integer totalPage;
    private Integer currentPage;
}

package com.gaotianchi.resource.web.response.page;

import com.gaotianchi.resource.web.response.info.SeriesCoverInfo;
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

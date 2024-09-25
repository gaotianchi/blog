package com.gaotianchi.resource.web.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class PageAvatarInfo {
    private List<AvatarInfo> avatarInfoList;
    private Integer totalPage;
    private Integer currentPage;
}

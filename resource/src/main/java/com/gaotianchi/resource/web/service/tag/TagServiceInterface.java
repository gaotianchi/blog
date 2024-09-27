package com.gaotianchi.resource.web.service.tag;

import com.gaotianchi.resource.web.response.PageInfo;
import com.gaotianchi.resource.web.response.info.TagInfo;

import java.util.List;

public interface TagServiceInterface {
    TagInfo newTag(String name);

    void deleteTag(Long id);

    TagInfo getInfo(Long id);

    PageInfo<TagInfo> getPageInfo(Integer page);

    List<String> getAllNames();
}

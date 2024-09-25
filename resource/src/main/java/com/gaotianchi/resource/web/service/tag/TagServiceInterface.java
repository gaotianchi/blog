package com.gaotianchi.resource.web.service.tag;

import com.gaotianchi.resource.web.response.PageTagInfo;
import com.gaotianchi.resource.web.response.TagInfo;

import java.util.List;

public interface TagServiceInterface {
    TagInfo newTag(String name);

    void deleteTag(Long id);

    TagInfo getInfo(Long id);

    PageTagInfo getPageInfo(Integer page);

    List<String> getAllNames();
}
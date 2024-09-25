package com.gaotianchi.resource.web.service.tag;

import com.gaotianchi.resource.web.response.TagInfo;

public interface TagServiceInterface {
    TagInfo newTag(String name);

    void deleteTag(Long id);
}

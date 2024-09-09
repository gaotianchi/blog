package com.gaotianchi.resource.web.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewSeriesRequest {
    private Long coverId;
    private String name;
}

package com.guosh.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiIgnore
public class FileInfo {
    private String path;
}

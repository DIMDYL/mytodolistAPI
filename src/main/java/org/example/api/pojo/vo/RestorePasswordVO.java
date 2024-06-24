package org.example.api.pojo.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RestorePasswordVO {
    private String sEmail;
    private String hEmail;
}

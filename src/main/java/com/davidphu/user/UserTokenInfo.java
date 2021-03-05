package com.davidphu.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserTokenInfo {
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String tokenInfo;
    private String tenantId;
}

package com.perflibnetcracker.authenticationservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionInfoDTO {
    private String endTime;
    private Integer freeBookCount;
}

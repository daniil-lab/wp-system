package com.wp.system.request.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EditPlansRequest {
    private Integer plannedSpend;

    private Integer plannedEarn;
}

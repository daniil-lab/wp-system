package com.wp.system.request.category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class AddCategoryToFavoriteRequest {
    private UUID categoryId;
}

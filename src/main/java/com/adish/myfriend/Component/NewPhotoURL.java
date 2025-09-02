package com.adish.myfriend.Component;

import lombok.Data;
import lombok.NonNull;

@Data
public class NewPhotoURL {
    private String oldPhoto;
    private String newPhoto;
    @NonNull
    private String productId;
}

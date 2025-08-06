package com.adish.myfriend.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "user_post")
@NoArgsConstructor
public class UserPost {
    @Id
    private ObjectId id;
    @NonNull
    private String title;
    private String content;
    private String photoURL;
    private LocalDateTime date;

}

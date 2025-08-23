package com.adish.myfriend.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
    @JsonProperty("_id") // Serialize field as "_id" in JSON (optional, common with MongoDB)
    @JsonSerialize(using = ToStringSerializer.class) // Serialize ObjectId as hex string
    private ObjectId id;
    @NonNull
    private String title;
    private String content;
    private String photoURL;
    private String date;

}

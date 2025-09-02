package com.adish.myfriend.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data
public class User {
    @Id
    @JsonProperty("_id") // Serialize field as "_id" in JSON (optional, common with MongoDB)
    @JsonSerialize(using = ToStringSerializer.class) // Serialize ObjectId as hex string
    private ObjectId id;
    @Indexed(unique = true)
    @NonNull
    private String userName;
    @NonNull
    private String password;
    private String profilePhotoURL;
    private String profileProductId;
    private List<String> productIds = new ArrayList<>();
    private String gmailId;
    @DBRef
    private List<UserPost> userPostsList = new ArrayList<>();
    private List<String> roll;
}

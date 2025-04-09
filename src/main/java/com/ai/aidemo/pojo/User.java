package com.ai.aidemo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id     // correspond to _id
    Long id;
    @Field("username")      // the field name in MongoDB is 'username', which is inconsistent with the field here
    String name;
    Integer age;
    String password;
}

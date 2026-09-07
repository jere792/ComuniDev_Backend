package com.comunidev.comunidevbackend.test;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document(collection = "test")
public class TestEntity {
    @Id
    private String id;
    private String nombre;
}

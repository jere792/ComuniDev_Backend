package com.comunidev.comunidevbackend.category.domain;

import com.comunidev.comunidevbackend.shared.domain.AggregateRoot;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@Setter
@Document(collection = "categories")
@CompoundIndex(name = "idx_categories_user_orden", def = "{'userId': 1, 'orden': 1}")
public class Category extends AggregateRoot<String> {
    @Indexed
    private String userId;
    private String nombre;
    private String descripcion;
    private String icono;
    private String color;
    private Integer orden = 0;
    private Boolean activa = true;
    private Instant createdAt;
    private Instant updatedAt;

    public static Category create(String userId, String nombre) {
        Category category = new Category();
        category.setUserId(userId);
        category.setNombre(nombre);
        category.setOrden(0);
        category.setActiva(true);
        category.setCreatedAt(Instant.now());
        category.setUpdatedAt(Instant.now());
        return category;
    }
}

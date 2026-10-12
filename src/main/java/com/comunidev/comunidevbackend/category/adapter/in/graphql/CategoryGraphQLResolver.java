package com.comunidev.comunidevbackend.category.adapter.in.graphql;

import com.comunidev.comunidevbackend.category.application.port.out.CategoryRepositoryPort;
import com.comunidev.comunidevbackend.category.domain.Category;
import com.comunidev.comunidevbackend.shared.exception.ResourceNotFoundException;
import com.comunidev.comunidevbackend.story.application.port.out.StoryRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class CategoryGraphQLResolver {

    private final CategoryRepositoryPort categoryRepositoryPort;
    private final StoryRepositoryPort storyRepositoryPort;

    @QueryMapping
    public List<Category> categories(@Argument String userId) {
        return categoryRepositoryPort.findByUserIdOrderByOrdenAsc(userId);
    }

    @QueryMapping
    public Category category(@Argument String id) {
        return categoryRepositoryPort.findById(id).orElse(null);
    }

    @MutationMapping
    public Category createCategory(
            @Argument String userId,
            @Argument String nombre,
            @Argument String descripcion,
            @Argument String icono,
            @Argument String color,
            @Argument Integer orden) {

        Category category = Category.create(userId, nombre);
        category.setDescripcion(descripcion);
        category.setIcono(icono);
        category.setColor(color);
        if (orden != null) {
            category.setOrden(orden);
        } else {
            category.setOrden(nextOrden(userId));
        }
        return categoryRepositoryPort.save(category);
    }

    private Integer nextOrden(String userId) {
        return categoryRepositoryPort.findByUserIdOrderByOrdenAsc(userId).stream()
                .map(Category::getOrden)
                .filter(Objects::nonNull)
                .max(Integer::compare)
                .map(max -> max + 1)
                .orElse(0);
    }

    @MutationMapping
    public Category updateCategory(
            @Argument String id,
            @Argument String nombre,
            @Argument String descripcion,
            @Argument String icono,
            @Argument String color,
            @Argument Integer orden,
            @Argument Boolean activa) {

        return categoryRepositoryPort.findById(id)
                .map(category -> {
                    if (nombre != null) category.setNombre(nombre);
                    if (descripcion != null) category.setDescripcion(descripcion);
                    if (icono != null) category.setIcono(icono);
                    if (color != null) category.setColor(color);
                    if (orden != null) category.setOrden(orden);
                    if (activa != null) category.setActiva(activa);
                    category.setUpdatedAt(Instant.now());
                    return categoryRepositoryPort.save(category);
                })
                .orElse(null);
    }

    @MutationMapping
    public Boolean deleteCategory(@Argument String id) {
        categoryRepositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        storyRepositoryPort.findByCategoriaId(id).forEach(story -> {
            if (story.getCategoriaIds() != null && story.getCategoriaIds().remove(id)) {
                storyRepositoryPort.save(story);
            }
        });
        categoryRepositoryPort.deleteById(id);
        return true;
    }
}

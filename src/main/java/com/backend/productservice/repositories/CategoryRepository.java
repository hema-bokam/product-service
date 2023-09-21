package com.backend.productservice.repositories;

import com.backend.productservice.models.Category;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

    @Query(value = "select c1.name from categories c1", nativeQuery = true)
    List<String> findAllNames();

    Optional<Category> findByNameEqualsIgnoreCase(String name);
}

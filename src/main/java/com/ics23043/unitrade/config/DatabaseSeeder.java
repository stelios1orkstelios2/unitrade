package com.ics23043.unitrade.config;

import com.ics23043.unitrade.models.Category;
import com.ics23043.unitrade.repositories.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    public DatabaseSeeder(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (categoryRepository.count() == 0) {
            Category books = new Category();
            books.setName("Books");

            Category electronics = new Category();
            electronics.setName("Electronics");

            Category furniture = new Category();
            furniture.setName("Furniture");

            Category clothing = new Category();
            clothing.setName("Clothing");

            categoryRepository.saveAll(List.of(books, electronics, furniture, clothing));
            System.out.println("DatabaseSeeder: Default categories initialized successfully.");
        }
    }
}

package com.rafaelhosaka.ecomm.category;

import com.github.javafaker.Cat;
import com.rafaelhosaka.ecomm.exception.CategoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll(){
        return categoryRepository.findAll();
    }

    public Category findById(Long id) throws CategoryNotFoundException{
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if(!optionalCategory.isPresent()){
            throw new CategoryNotFoundException("207","Category with ID " + id + " does not exist");
        }
        return  optionalCategory.get();
    }

    public void save(Category category){
        categoryRepository.save(category);
    }

    public Category findByName(String name){
        Optional<Category> categoryOptional = categoryRepository.findByName(name);
        if(!categoryOptional.isPresent()){
            //TODO throw exception
        }
        return categoryOptional.get();
    }
}

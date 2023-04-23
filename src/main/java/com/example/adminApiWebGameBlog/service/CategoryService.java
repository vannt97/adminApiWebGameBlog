package com.example.adminApiWebGameBlog.service;

import com.example.adminApiWebGameBlog.entity.CategoryEntity;
import com.example.adminApiWebGameBlog.enums.EStatusCategory;
import com.example.adminApiWebGameBlog.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");
    public long sizeCategory(){
        return categoryRepository.getSizeCategory();
    }

    public List<CategoryEntity> getCategories(){
        List<CategoryEntity> list = categoryRepository.findAll();
        return list;
    }
    public static String toSlug(String input) {
        String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }
    @Transactional
    public boolean editCategory(long id, String name){
        List<CategoryEntity> list = categoryRepository.findById(id);
        if(list.size()>0){
            CategoryEntity categoryEdit = list.get(0);
            categoryEdit.setName(name);
            categoryEdit.setModifiedDate((new Date()));
            categoryRepository.save(categoryEdit);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean deleteCategory(long id){
        try {
            categoryRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Transactional
    public boolean createCategory(String name){
        try {
            CategoryEntity category = new CategoryEntity();
            category.setName(name);
            category.setSlug(toSlug(name));
            category.setCreatedDate((new Date()));
            category.setModifiedDate((new Date()));
            category.setStatus(EStatusCategory.ACTIVE);
            categoryRepository.save(category);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}

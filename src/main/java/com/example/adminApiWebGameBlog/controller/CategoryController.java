package com.example.adminApiWebGameBlog.controller;

import com.example.adminApiWebGameBlog.dto.CategoryDTO;
import com.example.adminApiWebGameBlog.payload.reponse.ResponseData;
import com.example.adminApiWebGameBlog.service.BlogService;
import com.example.adminApiWebGameBlog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("count/categories")
    public ResponseEntity<?> getSizeBlog(){
        ResponseData response = new ResponseData();
        try {
            response.setSucces(true);
            response.setStatus(HttpStatus.OK.value());
            Map<String, Object> data = new HashMap<>();
            data.put("size", categoryService.sizeCategory());
            response.setData(data);
        }catch (Exception e){
            throw new ResponseStatusException(
                    HttpStatus.REQUEST_TIMEOUT, null, e);
        }
        return new ResponseEntity<ResponseData>(response, HttpStatus.OK);
    }

    @GetMapping("categories")
    public ResponseEntity<?> categories(){
        ResponseData response = new ResponseData();
        try {
            response.setSucces(true);
            response.setStatus(HttpStatus.OK.value());
            response.setData(categoryService.getCategories());
        }catch (Exception e){
            response.setSucces(false);
            response.setStatus(HttpStatus.REQUEST_TIMEOUT.value());
            response.setData("");
        }
        return new ResponseEntity<ResponseData>(response,HttpStatus.OK);
    }

    @PostMapping("create/category")
    public ResponseEntity<?> createCategory(@RequestBody Map<String,String> name){
        ResponseData responseData = new ResponseData();
        responseData.setSucces(false);
        responseData.setStatus(HttpStatus.OK.value());
        if(categoryService.createCategory(name.get("name"))){
            responseData.setSucces(true);
            responseData.setData("Đã tạo thành công");
        }
        return new ResponseEntity<ResponseData>(responseData,HttpStatus.OK);
    }

    @PostMapping("edit/category")
    public ResponseEntity<?> editCategory(@RequestBody CategoryDTO category){
        ResponseData responseData = new ResponseData();
        responseData.setSucces(false);
        responseData.setStatus(HttpStatus.OK.value());
        if(categoryService.editCategory(category.getId(),category.getName())){
            responseData.setSucces(true);
            responseData.setData("Đã edit thành công");
        }
        return new ResponseEntity<ResponseData>(responseData,HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}/category")
    public ResponseEntity<?> deleteCategory(@PathVariable long id){
        ResponseData responseData = new ResponseData();
        responseData.setSucces(false);
        responseData.setStatus(HttpStatus.OK.value());
        responseData.setData("Xoá không thành công");
        if(categoryService.deleteCategory(id)){
            responseData.setSucces(true);
            responseData.setData("Đã xoá thành công");
        }
        return new ResponseEntity<ResponseData>(responseData,HttpStatus.OK);
    }
}

package com.example.adminApiWebGameBlog.controller;

import com.example.adminApiWebGameBlog.dto.BlogDTO;
import com.example.adminApiWebGameBlog.dto.BlogEditDTO;
import com.example.adminApiWebGameBlog.entity.BlogEntity;
import com.example.adminApiWebGameBlog.enums.EStatus;
import com.example.adminApiWebGameBlog.payload.reponse.ResponseBlog;
import com.example.adminApiWebGameBlog.payload.reponse.ResponseData;
import com.example.adminApiWebGameBlog.service.BlogService;
import org.apache.tomcat.util.log.SystemLogHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.rmi.server.ExportException;
import java.util.*;

@CrossOrigin
@RestController
public class BlogController {

    @Autowired
    BlogService blogService;

    @GetMapping("blogs")
    public ResponseEntity<?> getBlogs(@RequestParam(required = false) Optional<String> status, @RequestParam(required = false) Optional<String> category ){
        ResponseData responseData = new ResponseData();

        try {
            responseData.setSucces(true);
            responseData.setStatus(HttpStatus.OK.value());
            if(!status.isPresent() && !category.isPresent()){
                responseData.setData(blogService.getBlogs());
            }else {
                responseData.setData(blogService.getBlogsByStatus(status.get()));
            }
        }catch (Exception ignored){
            responseData.setSucces(false);
            responseData.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseData.setData(ignored.getMessage());
        }
        return new ResponseEntity<Object>(responseData, HttpStatus.OK);
    }

    @GetMapping("blogs/pagination")
    public ResponseEntity<?> getBlogsPagination(@RequestParam(required = false) Optional<String> status,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "12") int size,
                                                @RequestParam(defaultValue = "", required = false) Optional<String> search,
                                                @RequestParam(required = false) Optional<String> category){
        ResponseData responseData = new ResponseData();
        if(status.isPresent()){
            responseData.setSucces(true);
            responseData.setStatus(HttpStatus.OK.value());
            if(EStatus.valueOf(status.get()).equals(EStatus.PUBLISH)){
                Pageable paging = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));
                responseData.setData(blogService.getBlogsPagetion(status.get(),paging,search.get(),category));
            }else {
                responseData.setData(new ArrayList<>());
            }
        }else {
            responseData.setSucces(false);
            responseData.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseData.setData(new ArrayList<>());
        }
        return new ResponseEntity<Object>(responseData, HttpStatus.OK);
    }

    @GetMapping("blog/{slug}")
    public ResponseEntity<?> getBlog(@PathVariable(required = false) String slug){
        ResponseData responseData = new ResponseData();
        List<ResponseBlog> blogs = blogService.getBlog(slug);
        if (blogs.size() > 0){
            responseData.setSucces(true);
            responseData.setStatus(HttpStatus.OK.value());
            responseData.setData(blogs.get(0));
            return new ResponseEntity<Object>(responseData,HttpStatus.OK);
        }else {
            responseData.setSucces(false);
            responseData.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseData.setData("");
            return new ResponseEntity<Object>(responseData,HttpStatus.CONFLICT);
        }

    }

    @GetMapping("/blog/id/{id}")
    public ResponseEntity<?> getBlogById(@PathVariable(required = false) long id){
        ResponseData responseData = new ResponseData();
        Optional<BlogEntity> blogs = blogService.getBlogById(id);
        if (blogs.isPresent()){
            responseData.setSucces(true);
            responseData.setStatus(HttpStatus.OK.value());
            responseData.setData(blogs.get());
            return new ResponseEntity<Object>(responseData,HttpStatus.OK);
        }else {
            responseData.setSucces(false);
            responseData.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseData.setData("");
            return new ResponseEntity<Object>(responseData,HttpStatus.CONFLICT);
        }
    }

    @PutMapping("blog/create")
    public ResponseEntity<?> createBlog(@RequestBody BlogDTO blog){
        ResponseData responseData = new ResponseData();
        blogService.createBlog(blog);
        responseData.setStatus(HttpStatus.OK.value());
        responseData.setSucces(true);
        responseData.setData("tạo thành công");
        return new ResponseEntity<ResponseData>(responseData,HttpStatus.OK);
    }

    @PostMapping("blog/edit")
    public ResponseEntity<?> editBlog(@RequestBody BlogEditDTO blog){
        ResponseData responseData = new ResponseData();
        blogService.editBlog(blog);
        responseData.setStatus(HttpStatus.OK.value());
        responseData.setSucces(true);
        responseData.setData("edit thành công");
        return new ResponseEntity<Object>(responseData,HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}/blog")
    public ResponseEntity<?> deleteBlog(@PathVariable(required = true) long id){
        ResponseData responseData = new ResponseData();
        boolean isDelete = blogService.deleteBlog(id);
        if(isDelete){
            responseData.setSucces(true);
            responseData.setStatus(HttpStatus.OK.value());
            responseData.setData("Delete success");
        }else {
            responseData.setSucces(false);
            responseData.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseData.setData("Delete failed");
        }
        return new ResponseEntity<Object>(responseData,HttpStatus.OK);
    }

    @GetMapping("count/blogs")
    public ResponseEntity<?> getSizeBlog(){
        ResponseData response = new ResponseData();
        try {
            response.setSucces(true);
            response.setStatus(HttpStatus.OK.value());
            Map<String, Object> data = new HashMap<>();
            data.put("size", blogService.sizeBlog());
            response.setData(data);
        }catch (Exception e){
            response.setSucces(false);
            response.setStatus(HttpStatus.REQUEST_TIMEOUT.value());
            response.setData("Khong co du lieu");
        }
        return new ResponseEntity<ResponseData>(response, HttpStatus.OK);
    }


    @PostMapping("blogs/add-links-images")
    public String addAllLink(){

        blogService.saveAllBlogsLinkImage();
        return "Thanh cong";
    }

}

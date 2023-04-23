package com.example.adminApiWebGameBlog.service;

import com.example.adminApiWebGameBlog.dto.BlogDTO;
import com.example.adminApiWebGameBlog.dto.BlogEditDTO;
import com.example.adminApiWebGameBlog.entity.AccountEntity;
import com.example.adminApiWebGameBlog.entity.BlogEntity;
import com.example.adminApiWebGameBlog.entity.CategoryEntity;
import com.example.adminApiWebGameBlog.enums.EStatus;
import com.example.adminApiWebGameBlog.expections.DeleteFailedException;
import com.example.adminApiWebGameBlog.expections.FileCanntUploadException;
import com.example.adminApiWebGameBlog.expections.NoAnyElementException;
import com.example.adminApiWebGameBlog.expections.SaveFailedException;
import com.example.adminApiWebGameBlog.payload.reponse.ResponseBlog;
import com.example.adminApiWebGameBlog.repository.AccountRepository;
import com.example.adminApiWebGameBlog.repository.BlogRepository;
import com.example.adminApiWebGameBlog.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BlogService {

    @Autowired
    BlogRepository blogRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CategoryRepository categoryRepository;

    public List<BlogEntity> getBlogs(){
        return blogRepository.findAllByOrderByIdDesc();
    }

    public Page<BlogEntity> getBlogsPagetion(String status, Pageable pageable, String search, Optional<String> categorySlug){
//        return blogRepository.findByStatusAndTitle(EStatus.PUBLISH,"blog",pageable);
        if(categorySlug.isPresent()){
            try {
                Optional<CategoryEntity> category = categoryRepository.findBySlug(categorySlug.get());
                return blogRepository.findByCategories(category.get(),pageable);
            }catch (Exception e){
                throw new NoAnyElementException();
            }
        }else {
            return blogRepository.findByStatusAndTitleContains(EStatus.PUBLISH,search,pageable);
        }
    }

    public List<ResponseBlog> getBlog(String slug){
        List<BlogEntity> result = blogRepository.findBySlug(slug);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy");
        List<ResponseBlog> blogs = result.stream().map(i -> {
            ResponseBlog blog = new ResponseBlog();
            blog.setId(i.getId());
            blog.setContent(i.getContent());
            blog.setSlug(i.getSlug());
            blog.setTitle(i.getTitle());
            blog.setStatus(i.getStatus());
            blog.setThumbnail(i.getThumbnail());
            blog.setDescription(i.getDescription());
            blog.setCategories(i.getCategories());
            blog.setModifiedDate(i.getModifiedDate().toString());
            String createDate = dateFormat.format(i.getCreatedDate());
            blog.setCreatedDate(createDate);
            return blog;
        }).collect(Collectors.toList());
        return blogs;
    }

    public Optional<BlogEntity> getBlogById(long id){
        return blogRepository.findById(id);
    }

    public List<?> getBlogsByStatus(String status) throws ParseException {
        if(EStatus.valueOf(status).equals(EStatus.PUBLISH)){
            List<BlogEntity> result = blogRepository.findTop12ByStatusOrderByCreatedDateDesc(EStatus.PUBLISH);
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy");
            List<ResponseBlog> blogs = result.stream().map(i -> {
                ResponseBlog blog = new ResponseBlog();
                blog.setId(i.getId());
                blog.setContent(i.getContent());
                blog.setSlug(i.getSlug());
                blog.setTitle(i.getTitle());
                blog.setStatus(i.getStatus());
                blog.setThumbnail(i.getThumbnail());
                blog.setDescription(i.getDescription());
                blog.setCategories(i.getCategories());
                blog.setModifiedDate(i.getModifiedDate().toString());
                String createDate = dateFormat.format(i.getCreatedDate());
                blog.setCreatedDate(createDate);
                return blog;
            }).collect(Collectors.toList());
            return blogs;
        }else {
            return new ArrayList<>();
        }
    }

    @Transactional
    public boolean deleteBlog(long id){
        try {
            blogRepository.deleteById(id);
            return true;
        }catch (Exception exception){
            throw new DeleteFailedException("failed delete");
        }
    }

    @Transactional
    public void createBlog(BlogDTO blog){
        try{
            List<AccountEntity> accounts =  accountRepository.findByUsername(blog.getAccountName());
            BlogEntity newBlog = new BlogEntity();
            newBlog.updateDate();
            newBlog.setContent(blog.getContent());
            newBlog.setSlug(blog.getSlug());
            newBlog.setTitle(blog.getTitle());
            newBlog.setAccount(accounts.get(0));
            newBlog.setStatus(blog.getStatus());
            newBlog.setThumbnail(blog.getThumbnail());
            newBlog.setDescription(blog.getDescription());

//            -cach 1
//            Set<BlogCategoryEntity> list = new HashSet<>();
//            blog.getCategories().forEach(numb -> {
//                BlogCategoryEntity blogCategory = new BlogCategoryEntity();
//                blogCategory.setCategory(categoryRepository.findById(numb).get(0));
//                blogCategory.setBlog(newBlog);
//                list.add(blogCategory);
//            });
//            newBlog.setCatogories(list);

//            -cach2
            blog.getCategories().forEach(numb -> {
                newBlog.addCategory(categoryRepository.findById(numb).get(0));
            });
            blogRepository.save(newBlog);

        }catch (Exception e){
            throw new SaveFailedException(e.getMessage());
        }
    }

    @Transactional
    public void editBlog(BlogEditDTO blog){
        try{
            List<AccountEntity> accounts =  accountRepository.findByUsername(blog.getAccountName());
            BlogEntity newBlog = blogRepository.findBySlug(blog.getSlugFirst()).get(0);
            newBlog.setModifiedDate((new Date()));
            newBlog.setContent(blog.getContent());
            newBlog.setSlug(blog.getSlug());
            newBlog.setTitle(blog.getTitle());
            newBlog.setAccount(accounts.get(0));
            newBlog.setStatus(blog.getStatus());
            newBlog.setThumbnail(blog.getThumbnail());
            newBlog.setDescription(blog.getDescription());
//          -cach1
//            Set<BlogCategoryEntity> collection = new HashSet<>();
//            for(BlogCategoryEntity blogCategory : newBlog.getCatogories()){
////                blogAccountRepository.findById(newBlog.getCatogories())
//            }

//            Set<BlogCategoryEntity> list = new HashSet<>();
//            blog.getCategories().forEach((numb) -> {
//                BlogCategoryEntity blogCategory = new BlogCategoryEntity();
//                CategoryEntity categoryNew = categoryRepository.findById(numb).get(0);
//                blogCategory.setCategory(categoryNew);
//                blogCategory.setBlog(newBlog);
//                list.add(blogCategory);
//            });
//
//            newBlog.setCatogories(newBlog.getCatogories());

//            cach2

//            blog.getCategories().forEach(numb -> {
//                newBlog.addCategory(categoryRepository.findById(numb).get(0));
//            });
            List<Long> listLong = newBlog.getCategories().stream().map(CategoryEntity::getId).collect(Collectors.toList());
            listLong.forEach(newBlog::removeCategory);

            blog.getCategories().forEach(numb -> {
                newBlog.addCategory(categoryRepository.findById(numb).get(0));
            });
            blogRepository.save(newBlog);

        }catch (Exception e){
            throw new SaveFailedException(e.getMessage());
        }
    }

    public long sizeBlog(){
        return blogRepository.getSizeBlog();
    }


    @Transactional
    public void saveAllBlogsLinkImage(){
       List<BlogEntity> blogs = blogRepository.findAll();
        String baseUrl =
                ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        String linkURLOld = "http://localhost:8081/api";
       List<BlogEntity> news = blogs.stream().map(i -> {
           i.setThumbnail(i.getThumbnail().replace(linkURLOld,baseUrl));
            i.setContent(i.getContent().replace(linkURLOld,baseUrl));
           blogRepository.save(i);
           return i;
       }).collect(Collectors.toList());
    }
}

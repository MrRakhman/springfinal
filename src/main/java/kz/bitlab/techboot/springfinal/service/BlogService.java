package kz.bitlab.techboot.springfinal.service;


import kz.bitlab.techboot.springfinal.model.Blog;
import kz.bitlab.techboot.springfinal.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {
    @Autowired
    private BlogRepository blogRepository;

    public Blog getBlog(Long id){
        return blogRepository.findById(id).orElse(null);
    }

    public List<Blog> getBlogsList(){
        return blogRepository.findAll();
    }

    public void addBlog(Blog blog){
        blogRepository.save(blog);
    }
}

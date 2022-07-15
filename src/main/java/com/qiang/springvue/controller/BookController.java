package com.qiang.springvue.controller;

import com.qiang.springvue.entity.Book;
import com.qiang.springvue.service.BookService;
//import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    BookService bookService;
/*
    @GetMapping("/all")
    public List<Book> getAll(){
        return bookService.getAllBook();
    }


    @GetMapping("/limit/{low}/{high}")
    public List<Book> getBookByLowAndHigh(@PathVariable("low") Integer low,@PathVariable("high") Integer high){
        return bookService.getBookByLowAndHigh(low, high);
    }

    @PostMapping("/")
    public int insertBook(@RequestBody  Book book){
        return bookService.insertBook(book);
    }

    @PutMapping("/emp")
    public int updateBook(@RequestBody Book book){
        return bookService.updateBook(book);
    }

    @DeleteMapping("/{id}")
    public int deleteBook(@PathVariable("id") Integer id){
        return bookService.deleteBook(id);
    }*/
}

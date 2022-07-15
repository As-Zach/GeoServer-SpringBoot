package com.qiang.springvue.service;

import com.qiang.springvue.entity.Book;
import com.qiang.springvue.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
   /* @Autowired
    BookMapper bookMapper;

    public List<Book> getAllBook(){
        return bookMapper.getAllBook();
    }

    public List<Book> getBookByLowAndHigh(Integer low,Integer high){
        return bookMapper.getBookByLowAndHigh(low, high);
    }

    public int insertBook(Book book){
        return bookMapper.insertBook(book);
    }

    public int updateBook(Book book){
        return bookMapper.updateBook(book);
    }

    public int deleteBook(Integer id){
        return bookMapper.deleteBook(id);
    }*/
}

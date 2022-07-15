package com.qiang.springvue.mapper;

import com.qiang.springvue.entity.Book;
//import org.apache.ibatis.annotations.*;

import java.util.List;

//@Mapper
public interface BookMapper {

   /* @Select("select * from book")
    public List<Book> getAllBook();

    @Select("select * from book limit #{low},#{high}")
    public List<Book> getBookByLowAndHigh(Integer low,Integer high);

    @Insert("insert into book(name,author) values(#{name},#{author})")
    public int insertBook(Book book);

    @Update("update book set name = #{name} , author = #{author} where id = #{id}")
    public int updateBook(Book book);

    @Delete("delete from book where id = #{id}")
    public int deleteBook(Integer id);*/
}

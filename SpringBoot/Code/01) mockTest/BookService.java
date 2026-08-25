package com.dsm.mentoringTest.mockTest;

import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BookService {
    private BookRepository bookRepository;

    public BookService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    public Book save(Book book){
        book.setCreated(new Date());
        book.setBookStatus(BookStatus.DRAFT);
        return bookRepository.save(book);
    }
}

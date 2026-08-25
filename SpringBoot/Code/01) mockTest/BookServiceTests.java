package com.dsm.mentoringTest;

import com.dsm.mentoringTest.mockTest.Book;
import com.dsm.mentoringTest.mockTest.BookRepository;
import com.dsm.mentoringTest.mockTest.BookService;
import com.dsm.mentoringTest.mockTest.BookStatus;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BookServiceTests {

    @Mock
    BookRepository bookRepository;

    @Test
    public void save(){
        Book book = new Book();

        when(bookRepository.save(book)).thenReturn(book);
        BookService bookService = new BookService(bookRepository);

        Book result = bookService.save(book);
        assertThat(book.getCreated()).isNotNull();
        assertThat(book.getBookStatus()).isEqualTo(BookStatus.DRAFT);
        assertThat(result).isNotNull();

    }
}

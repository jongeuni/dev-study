```java
@Repository
public class BookRepository {
    public Book save(Book book){
        return null;
    }
}
```

- 리포지터리는 현재 null을 return한다.

```java
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

```

- service의 save에서는 bookRepository의 save에 book을 매개값으로 넘겨준다.



이 상황에서 BookService의 save를 테스트 하고 싶다면 어떻게 해야 할까? BookRepository를 구현하는 수밖에 없나? 우리는 가짜객체를 만들 수 있다.



```java
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

```

`@Mock`: 가짜 객체를 만들어준다.

`when().thenReturn()`: 특정 메소드 호출 시 특정 값을 반환하도록 지정한다.

`when(bookRepository.save(book)).thenReturn(book)`: save 실행 시 매개값으로 온 book을 반환해준다.

**AssertJ**: junit에서 제공하는 assert보다 가독성이 올라감. 자바 라이브러리이다. 굉장히 직관적이다.

`assertThat().isNotNull()`: 안에 들어간 값이 null이 아니어야 한다.

`assertThat().isEqualsTo()`: 값이 같아야한다.
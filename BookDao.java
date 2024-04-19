package p3;

import java.util.List;
import java.util.Optional;

public interface BookDao {

    public Optional<Book> findByBookId(long bookId);

    public List<Book> findByCategoryId(long categoryId);

    public List<Book> findRandomBooks();

    public List<Book> findByCategoryName(String CategoryName);


    public List<Book> findRandomByCategoryName(String CategoryName, int limit);

    public List<Book> findAllBooks();

    public void addBook(long bookId, String title, String author, String description,
                        int price, int rating,
                        boolean isPublic, boolean isFeatured, long categoryId);

}

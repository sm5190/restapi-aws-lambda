package p3;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookDaoJdbc implements BookDao {

    static Statement st = null;
    static PreparedStatement pst = null;
    static Connection con = null;
    static MysqlDataSource source = null;
    static String name=System.getenv("username");
    static String pass=System.getenv("password");

    static String dbName = "p3ShutonuDatabase";
    static String url = "jdbc:mysql://p3shutonudatabase.c30okqgqayin.eu-west-2.rds.amazonaws.com:3306/" + dbName;
    static
    {
        try{
            source = new MysqlDataSource();
            source.setURL(url);
            source.setPassword(pass);
            source.setUser(name);
            con= source.getConnection();
            st = con.createStatement();

        }
        catch(SQLException e){
            System.out.println(e);
        }

    }

    private static final String ADD_BOOK_SQL = "INSERT INTO book (book_id, title, author, description, price, rating, is_public, is_featured, category_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String FIND_ALL_BOOKS_SQL =
            "SELECT book_id, title, author, description, price, rating, is_public, is_featured, category_id " +
                    "FROM book " ;

    private static final String FIND_BY_BOOK_ID_SQL =
            "SELECT book_id, title, author, description, price, rating, is_public, is_featured, category_id " +
                    "FROM book " +
                    "WHERE book_id = ?";

    private static final String FIND_BY_CATEGORY_ID_SQL =
        "SELECT book_id, title, author, description, price, rating, is_public, is_featured, category_id " +
                "FROM book " +
                "WHERE category_id = ?";

    private static final String FIND_BY_CATEGORY_NAME_SQL =
            "SELECT book_id, title, author, description, price, rating, is_public, is_featured, category.category_id, name " +
                    "FROM category, book " +
                    "WHERE category.name = ? " +
                    "AND category.category_id = book.category_id";


    private static final String FIND_RANDOM_SQL =
            "SELECT book_id, title, author, description, price, rating, is_public, is_featured, category_id  " +
                    "FROM book " +
                    "ORDER BY RAND() " +
                    "LIMIT 5";



    @Override
    public List<Book> findAllBooks() {
        List<Book> books = new ArrayList<>();
        try (
                PreparedStatement statement = con.prepareStatement(FIND_ALL_BOOKS_SQL);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Book book = readBook(resultSet);
                books.add(book);
            }
        } catch (SQLException e) {
        }
        return books;
    }

    public void addBook(long bookId, String title, String author, String description,
                        int price, int rating,
                        boolean isPublic, boolean isFeatured, long categoryId) {
        try (PreparedStatement statement = con.prepareStatement(ADD_BOOK_SQL)) {


            statement.setLong(1,bookId);
            statement.setString(2,title);
            statement.setString(3,author);
            statement.setString(4,description);
            statement.setInt(5,price);
            statement.setInt(6,rating);
            statement.setBoolean(7,isPublic);
            statement.setBoolean(8,isFeatured);
            statement.setLong(9,categoryId);

            int result = statement.executeUpdate();
            //con.commit();
            System.out.println("Rows affected: " + result); // Check how many rows were actually inserted
        } catch (SQLException e) {
            System.out.println("Error adding category: " + e.getMessage());
            e.printStackTrace(); // This will help you see the full stack trace
        }

    }
    @Override
    public Optional<Book> findByBookId(long bookId) {
        try (
                PreparedStatement statement = con.prepareStatement(FIND_BY_BOOK_ID_SQL)) {
            statement.setLong(1, bookId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(readBook(resultSet));
                }
            }
        } catch (SQLException e) {
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findByCategoryId(long categoryId) {
        List<Book> books = new ArrayList<>();

        try (
             PreparedStatement statement = con.prepareStatement(FIND_BY_CATEGORY_ID_SQL)) {
            statement.setLong(1, categoryId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while(resultSet.next()) {
                    Book book = readBook(resultSet);
                    books.add(book);
                }
            }
        } catch (SQLException e) {
            
        }


        return books;
    }

    @Override
    public List<Book> findRandomBooks() {
        List<Book> books = new ArrayList<>();

        try (PreparedStatement statement = con.prepareStatement(FIND_RANDOM_SQL);) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Book book = readBook(resultSet);
                books.add(book);
            }
        } catch (SQLException e) {
        }

        return books;
    }

    @Override
    public List<Book> findByCategoryName(String CategoryName) {
        List<Book> books = new ArrayList<>();

        try (
             PreparedStatement statement = con.prepareStatement(FIND_BY_CATEGORY_NAME_SQL)) {
            statement.setString(1, CategoryName);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Book book = readBook(resultSet);
                    books.add(book);
                }
            }
        } catch (SQLException e) {
            
        }


        return books;
    }


    @Override
    public List<Book> findRandomByCategoryName(String CategoryName, int limit) {
        List<Book> books = new ArrayList<>();


        try (
             PreparedStatement statement = con.prepareStatement(FIND_BY_CATEGORY_NAME_SQL)) {
            statement.setString(1, CategoryName);
            statement.setInt(2, limit);
            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    Book book = readBook(resultSet);
                    books.add(book);
                }

            }
        } catch (SQLException e) {
            
        }


        return books;
    }



    private Book readBook(ResultSet resultSet) throws SQLException {
        // TODO add description, isFeatured, rating to Book results
        String description=resultSet.getString("description");
        boolean isFeatured = resultSet.getBoolean("is_featured");
        int rating= resultSet.getInt("rating");

        long bookId = resultSet.getLong("book_id");
        String title = resultSet.getString("title");
        String author = resultSet.getString("author");
        int price = resultSet.getInt("price");
        boolean isPublic = resultSet.getBoolean("is_public");
        long categoryId = resultSet.getLong("category_id");
        return new Book(bookId, title, author,description ,price,rating, isPublic,isFeatured ,categoryId);
    }
}

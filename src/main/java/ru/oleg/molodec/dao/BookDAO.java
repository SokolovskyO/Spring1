package ru.oleg.molodec.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.oleg.molodec.models.Book;
import java.util.ArrayList;
import java.util.List;


@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book show(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE book_id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO Book(author, book_name, book_year) VALUES(?, ?, ?)", book.getAuthor(), book.getBook_name(),
                book.getBook_year());
    }

    public void update(int id, Book updatedBook) {
        jdbcTemplate.update("UPDATE Book SET author=?, book_name=?, book_year=? WHERE book_id=?", updatedBook.getAuthor(),
                updatedBook.getBook_name(), updatedBook.getBook_year(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Book WHERE book_id=?", id);
    }

    public void release(int id) {
        jdbcTemplate.update("UPDATE Book SET person_id = NULL WHERE book_id=?", id);
    }

    public List<Book> getBookIdByAuthor(int id) {
        return new ArrayList<>(jdbcTemplate.query("SELECT Book.book_name FROM Book JOIN Person ON Book.person_id = Person.id WHERE Person.id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class)));
    }

}

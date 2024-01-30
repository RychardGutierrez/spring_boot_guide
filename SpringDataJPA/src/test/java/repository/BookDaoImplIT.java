//package repository;
//
//import com.db.JDBCTemplateDemo.TestDataUtil;
//import com.db.JDBCTemplateDemo.dao.impl.AuthorDaoImpl;
//import com.db.JDBCTemplateDemo.dao.impl.BookDaoImpl;
//import com.db.JDBCTemplateDemo.domain.Author;
//import com.db.JDBCTemplateDemo.domain.Book;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.List;
//import java.util.Optional;
//
//@SpringBootTest
//@ExtendWith(SpringExtension.class)
//public class BookDaoImplIT {
//    @Autowired
//    private BookDaoImpl bookDao;
//
//    @Autowired
//    private AuthorDaoImpl authorDao;
//
//    @Test
//    public void testThatBookCanBeCreate() {
//        Author author = TestDataUtil.createTestAuthor();
//        authorDao.create(author);
//
//        Book book = TestDataUtil.createTestBook();
//        bookDao.create(book);
//
//
//        Optional<Book> result = bookDao.findOne(book.getIsbn());
//
//        assert result.isPresent();
//        assert result.get().equals(book);
//    }
//
//    @Test
//    public void testThatMultipleAuthorCanBeCreateAndRecalled() {
//        Author author = TestDataUtil.createTestAuthor();
//        authorDao.create(author);
//
//        Author authorB = TestDataUtil.createTestAuthorB();
//        authorDao.create(authorB);
//
//        Book bookA = TestDataUtil.createTestBook();
//        bookDao.create(bookA);
//        Book bookB = TestDataUtil.createTestBookB();
//        bookDao.create(bookB);
//
//        List<Book> result = bookDao.find();
//
//        assert result.size() == 2;
//        assert result.contains(bookB);
//    }
//
//    @Test
//    void testThatBookCanBenUpdate() {
//        Author author = TestDataUtil.createTestAuthor();
//        authorDao.create(author);
//
//        Book bookA = TestDataUtil.createTestBook();
//        bookDao.create(bookA);
//
//        bookA.setTitle("UPDATE");
//        bookDao.update("123", bookA);
//
//        Optional<Book> result = bookDao.findOne(bookA.getIsbn());
//        assert result.get().equals(bookA);
//    }
//
//    @Test
//    void testThatBookCanBeDeleted() {
//        Author author = TestDataUtil.createTestAuthor();
//        authorDao.create(author);
//
//        Book bookA = TestDataUtil.createTestBook();
//        bookDao.create(bookA);
//
//        bookDao.delete(bookA.getIsbn());
//
//        Optional<Book> result = bookDao.findOne(bookA.getIsbn());
//        assert !result.isPresent();
//    }
//}

//package repository;
//
//import com.db.JDBCTemplateDemo.TestDataUtil;
//import com.db.JDBCTemplateDemo.dao.impl.AuthorDaoImpl;
//import com.db.JDBCTemplateDemo.domain.Author;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.List;
//import java.util.Optional;
//
//@SpringBootTest
//@ExtendWith(SpringExtension.class)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//public class AuthorDaoImplIT {
//
//    private final AuthorDaoImpl authorDao;
//
//    @Autowired
//    public AuthorDaoImplIT(final AuthorDaoImpl authorDao) {
//        this.authorDao = authorDao;
//    }
//
//    @Test
//    public void testThatAuthorCanBeCreate() {
//        Author author = TestDataUtil.createTestAuthor();
//
//        authorDao.create(author);
//        Optional<Author> result = authorDao.findOne(author.getId());
//
//        assert result.isPresent();
//        assert result.get().equals(author);
//    }
//
//    @Test
//    public void testThatMultipleAuthorsCanBeCreatedAndRecalled() {
//        Author authorA = TestDataUtil.createTestAuthor();
//        authorDao.create(authorA);
//        Author authorB = TestDataUtil.createTestAuthorB();
//        authorDao.create(authorB);
//
//        List<Author> result = authorDao.find();
//        assert result.size() == 2;
//        assert result.contains(authorA);
//    }
//
//    @Test
//    public void testThatAuthorCanBeUpdated() {
//        Author author = TestDataUtil.createTestAuthor();
//        authorDao.create(author);
//        author.setName("UPDATE");
//        authorDao.update(author.getId(), author);
//
//        Optional<Author> result = authorDao.findOne(author.getId());
//
//        assert result.get().getName().equals("UPDATE");
//    }
//
//    @Test
//    public void testThatAuthorCanBeDeleted(){
//        Author author = TestDataUtil.createTestAuthor();
//        authorDao.create(author);
//
//        authorDao.delete(author.getId());
//
//        Optional<Author> result = authorDao.findOne(author.getId());
//
//        assert !result.isPresent();
//    }
//}

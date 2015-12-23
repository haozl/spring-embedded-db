package com.example.test;

import com.example.dao.*;
import com.example.model.User;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class UserDaoTest {

    @Autowired
    UserDao userDao;


    @Test
    public void testFindByName() {
        User user = userDao.findByName("sa");
        Assert.assertNotNull(user);
    }

    @Test(expected = org.springframework.dao.EmptyResultDataAccessException.class)
    public void userNotExists() {
        User user = userDao.findByName("nobody");
    }

    @Test
    public void testFindAll() {
        List<User> users = userDao.findAll();
        Assert.assertEquals(users.size(), 3);
        for (User user : users) {
            System.out.println(user.toString());
        }
    }


    @Configuration
    static class Config {

        @Bean
        public DataSource dataSource() {
            // no need shutdown, EmbeddedDatabaseFactoryBean will take care of this
            EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
            EmbeddedDatabase db = builder
                    .setType(EmbeddedDatabaseType.H2)
                    .addScript("schema.sql")
                    .addScript("insert.sql")
                    .build();
            return db;
        }

        @Autowired
        DataSource dataSource;


//        @Bean
//        public JdbcTemplate getJdbcTemplate() {
//            return new JdbcTemplate(dataSource);
//        }

        @Bean
        public NamedParameterJdbcTemplate template() {
            return new NamedParameterJdbcTemplate(dataSource);
        }

        @Bean
        public UserDao userDao() {
            return new UserDaoImpl();
        }
    }
}

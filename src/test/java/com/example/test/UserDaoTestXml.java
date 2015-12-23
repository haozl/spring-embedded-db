package com.example.test;

import com.example.dao.UserDao;
import com.example.model.User;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring-config.xml")
public class UserDaoTestXml {


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


}

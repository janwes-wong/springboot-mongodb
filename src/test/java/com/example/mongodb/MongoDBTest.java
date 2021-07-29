package com.example.mongodb;

import com.example.mongodb.pojo.PageResult;
import com.example.mongodb.pojo.Person;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Janwes
 * @version 1.0
 * @package com.example.mongodb
 * @date 2021/2/21 20:00
 * @description
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MongoDBTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * MongoDB保存数据-增
     */
    @Test
    public void saveTest() {
        Person person = new Person();
        person.setId(ObjectId.get());
        person.setName("胡歌");
        person.setAge(25);
        person.setAddress("上海黄浦区");
        mongoTemplate.save(person);
    }

    /**
     * Query类：将语句进行封装或者添加排序之类的操作(用来封装所有条件的对象)
     * Criteria类：封装所有的语句，以方法的形式进行查询(用来构建条件的对象)
     */
    @Test
    public void findTest01() {
        // 查询年龄<30的用户
        Query query = new Query(Criteria.where("age").lt(30));
        List<Person> personList = mongoTemplate.find(query, Person.class, "person");
        for (Person person : personList) {
            System.out.println(person);
        }
    }

    /**
     * and查询-方式一
     * 查询相同的字段
     */
    @Test
    public void findAnd01() {
        // 查询年龄大于25且小于30的用户(25>age<30)
        Query query = new Query(Criteria.where("age").gt(25).lt(30));
        List<Person> personList = mongoTemplate.find(query, Person.class, "person");
        for (Person person : personList) {
            System.out.println(person);
        }
    }

    /**
     * and查询-方式二
     */
    @Test
    public void findAnd02() {
        // 查询年龄小于等于30且名字为"刘德华"
        // 创建条件查询对象，并拼接条件
        Query query = new Query(Criteria.where("age").lte(30).and("name").is("刘德华"));
        List<Person> personList = mongoTemplate.find(query, Person.class, "person");
        for (Person person : personList) {
            System.out.println(person);
        }
    }

    /**
     * and查询-方式三
     */
    @Test
    public void findAnd03() {
        // 查询年龄小于等于30且名字为"刘德华"
        // 创建条件查询对象，并拼接条件
        Query query = new Query();
        query.addCriteria(Criteria.where("age").lte(30)).addCriteria(Criteria.where("name").is("刘德华"));
        List<Person> personList = mongoTemplate.find(query, Person.class, "person");
        // 循环输出打印数据
        for (Person person : personList) {
            System.out.println(person);
        }
    }

    /**
     * or查询
     */
    @Test
    public void findOr() {
        // 创建单一条件查询对象
        Criteria criteria = new Criteria();
        Query query = new Query(criteria.orOperator(Criteria.where("name").is("彭于晏"), Criteria.where("name").is("刘德华")));
        List<Person> personList = mongoTemplate.find(query, Person.class, "person");
        for (Person person : personList) {
            System.out.println(person);
        }
    }

    /**
     * 模糊查询-完全匹配
     */
    @Test
    public void likeFind01() {
        Pattern pattern = Pattern.compile("^张$", Pattern.CASE_INSENSITIVE);
        Query query = new Query(Criteria.where("name").regex(pattern));
        List<Person> personList = mongoTemplate.find(query, Person.class);
        for (Person person : personList) {
            System.out.println(person);
        }
    }

    /**
     * 模糊查询-左匹配
     */
    @Test
    public void likeFind02() {
        Pattern pattern = Pattern.compile("^张.*$", Pattern.CASE_INSENSITIVE);
        Query query = new Query(Criteria.where("name").regex(pattern));
        List<Person> personList = mongoTemplate.find(query, Person.class);
        for (Person person : personList) {
            System.out.println(person);
        }
    }

    /**
     * 模糊查询-右匹配
     */
    @Test
    public void likeFind03() {
        Pattern pattern = Pattern.compile("^.*晏$", Pattern.CASE_INSENSITIVE);
        Query query = new Query(Criteria.where("name").regex(pattern));
        List<Person> personList = mongoTemplate.find(query, Person.class);
        for (Person person : personList) {
            System.out.println(person);
        }
    }

    /**
     * 模糊查询-模糊匹配
     */
    @Test
    public void likeFind04() {
        Pattern pattern = Pattern.compile("^.*张.*$", Pattern.CASE_INSENSITIVE);
        Query query = new Query(Criteria.where("name").regex(pattern));
        List<Person> personList = mongoTemplate.find(query, Person.class);
        for (Person person : personList) {
            System.out.println(person);
        }
    }

    @Test
    public void likeFind05() {
        Query query = new Query(Criteria.where("name").regex("刘"));
        List<Person> personList = mongoTemplate.find(query, Person.class);
        for (Person person : personList) {
            System.out.println(person);
        }
    }


    @Test
    public void findOneTest() {
        // 条件查询 根据指定姓名查询
        Query query = new Query(Criteria.where("name").is("胡歌"));
        // 查询单个对象
        Person person = mongoTemplate.findOne(query, Person.class);
        System.out.println(person);
    }

    @Test
    public void findAllTest() {
        List<Person> personList = mongoTemplate.findAll(Person.class);
        for (Person person : personList) {
            System.out.println(person);
        }
    }

    /**
     * MongoDB分页查询
     */
    @Test
    public void findPageTest() {
        // 当前页
        int currentPage = 1;
        // 每页大小
        int pageSize = 5;

        Query query = new Query();
        query.limit(pageSize).skip((currentPage - 1) * pageSize);
        // 查询每页数据
        List<Person> personList = mongoTemplate.find(query, Person.class);
        // 查询总记录数
        long totalCount = mongoTemplate.count(query, "person");// 也可以这样写 mongoTemplate.count(query, Person.class);
        // 总页数
        long page = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        for (Person person : personList) {
            System.out.println(person);
        }
        System.out.println("总记录数totalCount：" + totalCount);
        System.out.println("总页数：" + page);
    }

    /**
     * MongoDB分页查询-封装
     */
    @Test
    public void findPage() {
        // 当前页
        int currentPage = 1;
        // 每页大小
        int pageSize = 5;

        Query query = new Query();
        query.limit(pageSize).skip((currentPage - 1) * pageSize);
        // 查询每页数据
        List<Person> personList = mongoTemplate.find(query, Person.class);
        // 查询总记录数
        long totalCount = mongoTemplate.count(query, "person");// 也可以这样写 mongoTemplate.count(query, Person.class);

        PageResult pageResult = new PageResult(totalCount, (long) pageSize, (long) currentPage, personList);
        System.out.println(pageResult);
    }

    /**
     * MongoDB更新数据-改
     */
    @Test
    public void updateTest() {
        // 根据姓名修改年龄
        Query query = new Query(Criteria.where("name").is("刘德华"));
        mongoTemplate.updateFirst(query, Update.update("age", 21), Person.class);
    }

    @Test
    public void update() {
        // 根据id修改地址
        Query query = new Query(Criteria.where("id").is("6032536b1cdc8029a01df1be"));
        Update update = Update.update("address", "浙江杭州");
        mongoTemplate.updateFirst(query, update, Person.class);
    }

    /**
     * MongoDB删除数据
     */
    @Test
    public void deleteTest() {
        Query query = new Query(Criteria.where("id").is("6032536b1cdc8029a01df1bf"));
        mongoTemplate.remove(query, Person.class);
    }
}


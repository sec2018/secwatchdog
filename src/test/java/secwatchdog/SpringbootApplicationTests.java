package secwatchdog;

 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.google.gson.Gson;

import sec.secwatchdog.SecWatchDogApplication;
import sec.secwatchdog.redis.service.RedisService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SecWatchDogApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringbootApplicationTests {

    private Gson json = new Gson();

    @Autowired
    private RedisService redisService;

    @Test
    public void contextLoads() throws Exception {
    }


    /**
     * 插入字符串
     */
    @Test
    public void setString() {
        redisService.set("redis_string_test", "springboot redis test");
    }

    /**
     * 获取字符串
     */
    @Test
    public void getString() {
        String result = redisService.get("redis_string_test");
        System.out.println(result);
    }

    /**
     * 插入对象
     */
    @Test
    public void setObject() {
        Person person = new Person("person", "male");
        redisService.set("redis_obj_test", json.toJson(person));
    }

    /**
     * 获取对象
     */
    @Test
    public void getObject() {
        String result = redisService.get("redis_obj_test");
        Person person = json.fromJson(result, Person.class);
        System.out.println(json.toJson(person));
    }

    /**
     * 插入对象List
     */
    @Test
    public void setList() {
        Person person1 = new Person("person1", "male");
        Person person2 = new Person("person2", "female");
        Person person3 = new Person("person3", "male");
        List<Person> list = new ArrayList<>();
        list.add(person1);
        list.add(person2);
        list.add(person3);
        redisService.set("redis_list_test", json.toJson(list));
    }
    
    @Test
    public void setMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        Person person = new Person("person", "male");
        map.put("num",10);
        map.put("person",person);
        
        redisService.set("redis_map_test", json.toJson(map));
         
    }
    @Test
    public void getMap() {
    	 Map<String, Object> map = new HashMap<String, Object>();
    	String mapString =  redisService.get("redis_map_test");
    	map = json.fromJson(mapString, map.getClass());

    	System.out.println(map);
    	System.out.println(map.get("num"));
    	System.out.println(map.get("person"));
    }

    /**
     * 获取list
     */
    @Test
    public void getList() {
    	List<String> list = new ArrayList<>();
        String resulttemp = redisService.get("redis_list_test");
        list = json.fromJson(resulttemp, List.class);
       // List<String> list = Arrays.asList(result);
        System.out.println(list);
    }

    @Test
    public void remove() {
        redisService.remove("redis_map_test");
    }

}

class Person {
    private String name;
    private String sex;

    public Person() {

    }

    public Person(String name, String sex) {
        this.name = name;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
 
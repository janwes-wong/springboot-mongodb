package com.example.mongodb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @author Janwes
 * @version 1.0
 * @package com.example.mongodb.pojo
 * @date 2021/2/21 19:55
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "person") // 指定对应映射表名
public class Person implements Serializable {
    private ObjectId id;
    private String name;
    private Integer age;
    private String address;
}

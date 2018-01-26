package com.paranoia.signature.dto;

/**
 * @author PARANOIA_ZK
 * @date 2018/1/26 10:58
 */
public class Person {
    private String name ;
    private String age ;
    private String email ;
    private String sex ;

    public Person() {
    }

    public Person(String name, String age, String email, String sex) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", email='" + email + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}

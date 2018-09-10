package top.lxpsee;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/9/7 07:01.
 */
public class Person implements Serializable {
    private int         id;
    private String      name;
    private String      email;
    private PhoneNumber phoneNumber;

    public PhoneNumber getPhoneNumber() {
        return new PhoneNumber();
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PhoneNumber getInner() {
        return new PhoneNumber();
    }

    class PhoneNumber {
        private String number;
        private int    type;

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

    enum PhoneType {
        MOBILE(0, "手机"), HOME(1, "家庭"), WORK(2, "工作");

        private int    type;
        private String values;

        PhoneType(int type, String values) {
            this.type = type;
            this.values = values;
        }
    }

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();

        Person person = new Person();
        person.setId(12345);
        person.setName("toms");
        person.setEmail("helloworld@.com");

        Person.PhoneNumber phoneNumber = person.getInner();
        phoneNumber.setNumber("123 4567 8910");
        phoneNumber.setType(PhoneType.HOME.type);
        person.setPhoneNumber(phoneNumber);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("d:/java.data"));
        objectOutputStream.writeObject(person);
        objectOutputStream.close();

        /*ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("d:/java.data "));
        Person person = (Person) objectInputStream.readObject();
        System.out.println(person.getInner().getNumber());*/
        System.out.println(System.currentTimeMillis() - start);
    }

}

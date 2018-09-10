package lxpsee.top;

import org.junit.Test;
import top.lxpsee.AddressBookProtos;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/9/7 06:49.
 */
public class TestProtoBuf {


    @Test
    public void write() throws IOException {
        long start = System.currentTimeMillis();

        AddressBookProtos.Person person = AddressBookProtos.Person.newBuilder()
                .setId(12345)
                .setName("toms")
                .setEmail("helloworld@.com")
                .addPhone(AddressBookProtos.Person.PhoneNumber.newBuilder()
                        .setNumber("123 4567 8910")
                        .setType(AddressBookProtos.Person.PhoneType.HOME)
                        .build())
                .build();
        person.writeTo(new FileOutputStream("d:/prototbuf.data"));

        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    public void read() throws IOException {
        long start = System.currentTimeMillis();
        AddressBookProtos.Person person = AddressBookProtos.Person.parseFrom(new FileInputStream("d:/prototbuf.data"));
        System.out.println(person.getName());
        System.out.println(System.currentTimeMillis() - start);
    }


}

package lxpsee.top;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.junit.Test;
import top.lxpsee.avro.Employee;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/9/7 12:11.
 */
public class TestAvro {

    /**
     * 编译后再串行化
     */
    @Test
    public void write() throws Exception {
        SpecificDatumWriter empDatumWriter = new SpecificDatumWriter(Employee.class);
        DataFileWriter<Employee> employeeDataFileWriter = new DataFileWriter<Employee>(empDatumWriter);

        Employee employee = new Employee();
        employee.setAge(11);
        employee.setName("jim green");

        employeeDataFileWriter.create(employee.getSchema(), new FileOutputStream("d:/e1.avro"));
        employeeDataFileWriter.append(employee);
        employeeDataFileWriter.append(employee);
        employeeDataFileWriter.append(employee);

        employeeDataFileWriter.close();
    }

    @Test
    public void read() throws IOException {
        SpecificDatumReader specificDatumReader = new SpecificDatumReader(Employee.class);
        DataFileReader<Employee> employeeDataFileReader = new DataFileReader<Employee>(new File("d:/emp2.avro"), specificDatumReader);
        Iterator<Employee> iterator = employeeDataFileReader.iterator();
        int count = 1;

        while (iterator.hasNext()) {
            Employee e = iterator.next();
            System.out.println(e.getAge() + " : " + e.getName() + " " + count);
            count++;
        }
    }

    @Test
    public void writeInSchema() throws IOException {
        Schema schema = new Schema.Parser().parse(new File("D:\\Develop\\soft\\LPavro\\emp.avsc"));

        GenericRecord emp = new GenericData.Record(schema);
        emp.put("Name", "tom");
        emp.put("age", 25);

        GenericRecord emp2 = new GenericData.Record(schema);
        emp2.put("Name", "tom2");
        emp2.put("age", 24);

        GenericRecord emp3 = new GenericData.Record(schema);
        emp3.put("Name", "tom3");
        emp3.put("age", 21);

        GenericRecord emp4 = new GenericData.Record(schema);
        emp4.put("Name", "tom4");
        emp4.put("age", 223);

        DatumWriter<GenericRecord> emptu = new GenericDatumWriter<GenericRecord>(schema);
        DataFileWriter<GenericRecord> empfile = new DataFileWriter<GenericRecord>(emptu);
        empfile.create(schema, new File("d:/emp2.avro"));
        empfile.append(emp);
        empfile.append(emp2);
        empfile.append(emp3);
        empfile.append(emp4);
        empfile.close();

    }

}

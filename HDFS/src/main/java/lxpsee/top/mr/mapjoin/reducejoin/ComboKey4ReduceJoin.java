package lxpsee.top.mr.mapjoin.reducejoin;


import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/8/27 08:44.
 * <p>
 * 读写方法的顺序应该和上面的一致，不然出错，分行输出，属性应该初始化，易报空指针异常
 */
public class ComboKey4ReduceJoin implements WritableComparable<ComboKey4ReduceJoin> {
    private int type; // 0客户，1订单
    private int cid;
    private int oid;

    private String customerINfo = "";
    private String orderInfo    = "";

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public String getCustomerINfo() {
        return customerINfo;
    }

    public void setCustomerINfo(String customerINfo) {
        this.customerINfo = customerINfo;
    }

    public String getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }

    /**
     * 判断是否是同一个客户的数据，不是则根据用户id升序排序
     * 判断是否是同一个用户下的同一种类型，不是则用户排在订单前面，是则按订单id排序
     */
    public int compareTo(ComboKey4ReduceJoin o) {
        if (cid == o.cid) {
            if (type == o.type) {
                return oid - o.oid;
            } else {
                if (type == 0) {
                    return -1;
                } else {
                    return 1;
                }
            }
        } else {
            return cid - o.cid;
        }
    }

    public void write(DataOutput out) throws IOException {
        out.writeInt(type);
        out.writeInt(cid);
        out.writeInt(oid);
        out.writeUTF(customerINfo);
        out.writeUTF(orderInfo);
    }

    public void readFields(DataInput in) throws IOException {
        this.type = in.readInt();
        this.cid = in.readInt();
        this.oid = in.readInt();
        this.customerINfo = in.readUTF();
        this.orderInfo = in.readUTF();
    }
}

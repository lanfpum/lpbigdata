package lxpsee.top.rackaware;

import org.apache.hadoop.net.DNSToSwitchMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/8/23 16:33.
 */
public class MyRackAware implements DNSToSwitchMapping {
    public List<String> resolve(List<String> names) {
        List<String> nameList = new ArrayList<String>(names.size());
        String ipAdress = null;

        for (String name : names) {
            System.out.println(name);

            if (name.startsWith("192")) {
                ipAdress = name.substring(name.lastIndexOf(".") + 1);
            } else if (name.startsWith("ip")) {
                ipAdress = name.substring(2);
            }

            Integer ip = Integer.valueOf(ipAdress);

            if (ip % 2 == 0) {
                nameList.add("/rack1/" + ip);
            } else {
                nameList.add("/rack2/" + ip);
            }
        }

        return nameList;
    }

    public void reloadCachedMappings() {

    }

    public void reloadCachedMappings(List<String> names) {

    }
}

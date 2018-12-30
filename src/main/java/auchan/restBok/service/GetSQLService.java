package auchan.restBok.service;

import auchan.restBok.exceptions.NotFoundException;
import auchan.restBok.model.Data;
import auchan.restBok.util.SQLThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GetSQLService {

    @Autowired
    private Environment env;
    private String driver = "oracle.jdbc.driver.OracleDriver";
    private String name = "some_name";
    private String pass = "some_password";
    private String sql_template = "select some fields from some sql table where bill_num = %s" ;

    public Map<String, String> getPosData(String vte_date, String store, String check_num) {
        String url = env.getProperty("DBKS"+store);
        Map<String, String> res = new HashMap<>();

        if (!StringUtils.isEmpty(url)) {

            String sql = String.format(sql_template, vte_date, check_num).replace(";", "");

            SQLThread bok = new SQLThread(sql, driver, url, name, pass, "bok" + store + "_thread");
            List<Data> data = bok.executeSQLAndClose();


            if (data != null && data.size() > 1) {
                Data d = data.get(1);
                res.put("store", store);
                res.put("cashdesk_n", d.get(0).toString());
                res.put("ticket_n", d.get(1).toString());
                res.put("amount", d.get(2).toString());
                return res;
            } else {
                res.put("error", "No data found with this values: " +
                        "store = " + store + " " +
                        "check_num = " + check_num + " " +
                        "date = " + vte_date
                );
            }

        } else {
            res.put("error", "No Url found in properties for name DBKS"+store);
        }
        return res;

    }

    private String getBokUrl(String value) {
        String res = "DBKS" + env.getProperty(value);
        if (!StringUtils.isEmpty(res)) return res;
        else throw new NotFoundException("");
    }

    private void print(String s) {
        System.out.println(s);
    }


}

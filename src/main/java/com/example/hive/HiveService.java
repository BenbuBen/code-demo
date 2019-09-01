package com.example.hive;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * hive service
 * 
 * @author ben 2019年8月18日
 */
public class HiveService {

    public static int hiveJDBC_RowCount(String sql, Map<Integer, String> params) {
	try {
	    ResourceBundle rb = ResourceBundle.getBundle("config");
	    Class.forName(rb.getString("hivedriverClassName")).newInstance();
	    Connection conn = DriverManager.getConnection(rb.getString("hiveurl"), rb.getString("hiveusername"),
		    rb.getString("hivepassword"));
	    java.sql.PreparedStatement pstsm = conn.prepareStatement(sql);
	    for (Integer key : params.keySet()) {
		pstsm.setString(key, params.get(key));
	    }
	    ResultSet resultSet = pstsm.executeQuery();
	    int rowNum = 0;
	    if (resultSet.next()) {
		rowNum = resultSet.getInt(1);
	    }
	    return rowNum;
	} catch (Exception e) {
	    System.out.println(e);
	    return 0;
	}
    }
}

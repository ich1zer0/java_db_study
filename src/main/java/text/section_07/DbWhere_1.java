package text.section_07;

import text.section_03.DbConnect_1;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Objects;
import java.util.Properties;

public class DbWhere_1 {
    public static void main(String[] args) {
        Connection con = null;
        Statement statement = null;

        try {
            InputStream is = Objects.requireNonNull(DbConnect_1.class.getResource("/database.properties")).openStream();
            if (is == null) {
                throw new IOException("プロパティファイルが見つかりません: /database.properties");
            }

            Properties props = new Properties();
            props.load(is);

            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");

            con = DriverManager.getConnection(
                    url,
                    user,
                    password
            );

            System.out.println("データベース接続成功");

            statement = con.createStatement();
            String sql = "SELECT * FROM users WHERE age >= 25;";

            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                int age = result.getInt("age");
                System.out.println(result.getRow() + "件目：id=" + id
                        + "／name=" + name + "／age=" + age);
            }
        } catch (IOException | SQLException e) {
            System.out.println("データベース接続失敗：" + e.getMessage());
        } finally {
            // 使用したオブジェクトを解放
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ignore) {
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ignore) {
                }
            }
        }
    }
}

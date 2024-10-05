package text.section_03;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public class DbConnect_1 {
    public static void main(String[] args) {
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

            Connection con = DriverManager.getConnection(
                    url,
                    user,
                    password
            );

            System.out.println("データベース接続成功");
            System.out.println(con);

            con.close();
        } catch (IOException | SQLException e) {
            System.out.println("データベース接続失敗：" + e.getMessage());
        }
    }
}

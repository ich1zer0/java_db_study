package text.section_09;

import text.section_03.DbConnect_1;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Properties;

public class DbDelete_1 {
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
            String sql = "DELETE FROM users WHERE id = 6;";

            System.out.println("レコード削除:" + statement.toString());
            int rowCnt = statement.executeUpdate(sql);
            System.out.println(rowCnt + "件のレコードが削除されました");
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

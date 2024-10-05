package text.section_05;

import text.section_03.DbConnect_1;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public class DbInsert_1 {
    public static void main(String[] args) {
        Connection con = null;
        PreparedStatement statement = null;

        String[][] userList = {
                {"侍一郎", "28"},
                {"侍花子", "24"},
                {"侍二郎", "26"},
                {"侍寺子", "37"},
                {"侍三郎", "21"}
        };

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

            String sql = "INSERT INTO users (name, age) VALUES (?, ?);";
            statement = con.prepareStatement(sql);

            int rowCnt;
            for (String[] strings : userList) {
                // SQLクエリの「?」部分をリストのデータに置き換え
                statement.setString(1, strings[0]); // 名前
                statement.setString(2, strings[1]); // 年齢

                // SQLクエリを実行（DBMSに送信）
                System.out.println("レコード追加:" + statement);
                rowCnt = statement.executeUpdate();
                System.out.println(rowCnt + "件のレコードが追加されました");
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

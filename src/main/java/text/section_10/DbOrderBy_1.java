package text.section_10;

import text.section_03.DbConnect_1;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Objects;
import java.util.Properties;
import java.util.Scanner;

public class DbOrderBy_1 {
    public static void main(String[] args) {
        Scanner scanner = null;
        Connection con = null;
        Statement statement = null;

        try {
            System.out.println("0(昇順)か1(降順)を入力してください：");
            scanner = new Scanner(System.in);
            String order = switch (scanner.nextInt()) {
                case 0 -> "ASC;";
                case 1 -> "DESC;";
                default -> "ASC;"; // デフォルトは昇順扱い
            };

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
            String sql = "SELECT * FROM users ORDER BY age " + order;

            System.out.println("データ取得を実行：" + sql);
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                int age = result.getInt("age");
                System.out.println(result.getRow() + "件目：id=" + id + "／name=" + name + "／age=" + age);
            }
        } catch (IOException | SQLException e) {
            System.out.println("データベース接続失敗：" + e.getMessage());
        } finally {
            // 使用したオブジェクトを解放
            if (scanner != null) {
                scanner.close();
            }
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

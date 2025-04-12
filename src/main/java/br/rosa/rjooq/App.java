package br.rosa.rjooq;

import static br.rosa.rjooq.Tables.AUTHOR;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

/**
 * Rogoo
 */
public class App {

	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306";
		String user = "root";
		String password = "root";

		System.out.println("Starting...");

		try (Connection conn = DriverManager.getConnection(url, user, password)) {
			Class.forName("com.mysql.cj.jdbc.Driver");

			Statement st = conn.createStatement();
			String sql = "use `library`";
			st.execute(sql);
			System.out.println("Changed schema with success");

			DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
			Result<org.jooq.Record> result = create.select().from(AUTHOR).fetch();
			for (Record rec : result) {
				Integer id = rec.getValue(AUTHOR.ID);
				String firstName = rec.getValue(AUTHOR.FIRST_NAME);
				String lastName = rec.getValue(AUTHOR.LAST_NAME);

				System.out.println("ID: " + id + " first name: " + firstName + " last name: " + lastName);
			}
			System.out.println();
		} catch (Exception e) {
			System.out.println("Fim COM CAQUINHA");
			e.printStackTrace();
		}
	}
}
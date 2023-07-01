package application;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import db.Db;
import db.DbException;

public class Program {

	public static void main(String[] args) {
		Connection conn = null;

		Statement st = null;
//manter a consistencia do banco de dados

		// setAutoCommit = cada operação isolada que voce fizer ela vai ser confirmada
		// automaticamente
		// commit => confirmar a transação
		// rollback desfazer o que foi feito ate o momento;
		try {
			conn = Db.getConnection();
			conn.setAutoCommit(false); // Não e para confirma a transação automaticamente
			st = conn.createStatement();

			int rows1 = st.executeUpdate("UPDATE " + "seller " + "SET BaseSalary = 2090 " + "WHERE DepartmentId = 1");
			int x = 4;

			if (x < 2) {
				throw new SQLException("Fake Error");
			}
			int rows2 = st.executeUpdate("UPDATE " + "seller " + "SET BaseSalary = 3090 " + "WHERE DepartmentId = 2");

			conn.commit(); // vai confirmar as transações
			System.out.println("rows1 " + rows1);
			System.out.println("rows1 " + rows2);

		} catch (SQLException e) {
			try {
				conn.rollback();
				throw new DbException("Transaction rolled back! Caused by: " + e.getMessage());
			} catch (SQLException e1) {

				throw new DbException("Error trying to rollback! Caused by: " + e1.getMessage());
			}

		} finally {
			Db.closeStatement(st);
			Db.closeConnection();
		}

	}

}

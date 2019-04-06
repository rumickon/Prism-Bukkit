package me.botsko.prism.database.mysql;

import me.botsko.prism.Prism;
import me.botsko.prism.database.DeleteQuery;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLDeleteQueryBuilder extends MySQLSelectQueryBuilder implements DeleteQuery {

	/**
	 * 
	 */
	public MySQLDeleteQueryBuilder() {
		super();
	}

	/**
	 * 
	 */
	@Override
	protected String select() {
		return "DELETE FROM " + tableNameData + " USING " + tableNameData + " LEFT JOIN " + tableNameDataExtra
				+ " ex ON (" + tableNameData + ".id = ex.data_id) ";
	}

	/**
	 * 
	 */
	@Override
	protected String group() {
		return "";
	}

	/**
	 * 
	 */
	@Override
	protected String order() {
		return "";
	}

	/**
	 * 
	 */
	@Override
	protected String limit() {
		return "";
	}

	@Override
	public int execute() {
		int total_rows_affected = 0;
		int cycle_rows_affected = 0;
		try(
				Connection connection = Prism.getPrismDataSource().getDataSource().getConnection();
				Statement s = connection.createStatement();
				){
			cycle_rows_affected = s.executeUpdate(getQuery(parameters,shouldGroup));
			total_rows_affected += cycle_rows_affected;
		} catch (final SQLException e) {
			Prism.getPrismDataSource().handleDataSourceException(e);
		}
		return total_rows_affected;
	}
}
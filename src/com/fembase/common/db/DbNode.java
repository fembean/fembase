package com.fembase.common.db;

import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

/**
 * 
 * @author
 * 一個Db
 */
public class DbNode {

	private ComboPooledDataSource pool;

	public DbNode(DbNodeConfig config) {
		pool = createPool(config);
	}

	public Connection getConnection() throws SQLException {
		return pool.getConnection();
	}

	public void destroy() throws SQLException {
		DataSources.destroy(pool);
	}

	private static ComboPooledDataSource createPool(DbNodeConfig config) {
		ComboPooledDataSource ds = new ComboPooledDataSource();

		try {
			ds.setDriverClass(config.driverClass);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Error("unsupported driver : " + config.driverClass);
		}

		String connStr = String.format("jdbc:mysql://%s/%s?user=%s&password=%s&autoReconnect=true&useUnicode=true&characterEncoding=utf8", 
				config.ip,config.dbName, config.userName, config.password);

		ds.setJdbcUrl(connStr);
		ds.setMinPoolSize(config.minPoolSize);
		ds.setMaxPoolSize(config.maxPoolSize);
		ds.setMaxIdleTime(config.maxIdleTime);
		ds.setCheckoutTimeout(config.checkOutTimeOut);
		ds.setPreferredTestQuery(config.testSql);
		ds.setTestConnectionOnCheckin(config.testConnOnCheckin);

		return ds;
	}
}

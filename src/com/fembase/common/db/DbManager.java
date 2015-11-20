package com.fembase.common.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fembase.common.utils.ConfigManager;




/**
 * @author 管理所有的Db实例
 */
public class DbManager {

	protected Log log = LogFactory.getLog(getClass());

    private static final DbManager instance = new DbManager();

    private DbNode dbNode;

    public static DbManager getInstnace() {
        return instance;
    }

    private DbManager() {
        DbNodeConfig config = new DbNodeConfig();
        config.checkOutTimeOut = Integer.valueOf(ConfigManager.instance.getValue("db.config.checkOutTimeOut"));
        config.dbName = ConfigManager.instance.getValue("db.config.dbName");
        config.ip = ConfigManager.instance.getValue("db.config.ip");
        config.maxIdleTime = Integer.valueOf(ConfigManager.instance.getValue("db.config.maxIdleTime"));
        config.maxPoolSize = Integer.valueOf(ConfigManager.instance.getValue("db.config.maxPoolSize"));
        config.minPoolSize = Integer.valueOf(ConfigManager.instance.getValue("db.config.minPoolSize"));
        config.driverClass = ConfigManager.instance.getValue("db.config.driverClass");
        config.userName = ConfigManager.instance.getValue("db.config.userName");
        config.password = ConfigManager.instance.getValue("db.config.password");
        config.testConnOnCheckin = Boolean.valueOf(ConfigManager.instance .getValue("db.config.testConnOnCheckin"));
        config.testSql = "SELECT * FROM T_ConnectTest;";

        dbNode = new DbNode(config);
    }

    public int executeCommand(String sql, Object[] params) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DbManager.getInstnace().getConnection();
            stmt = conn.prepareStatement(sql);
            setParams(stmt, params);
            return stmt.executeUpdate();
        } catch (Exception e) {
        	log.error(String.format("exception in sql =  %s  %s", sql, params != null ? Arrays.toString(params): ""),e);
            e.printStackTrace();
            return 0;
        } finally {
            releaseDbResource(conn, stmt, rs);
        }
    }
    
    /**
     * 不抛异常，不打印异常信息
     * @param sql
     * @param params
     * @return
     */
    public int executeCommandPrintNo(String sql, Object[] params) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DbManager.getInstnace().getConnection();
            stmt = conn.prepareStatement(sql);
            setParams(stmt, params);
            return stmt.executeUpdate();
        } catch (Exception e) {
            return 0;
        } finally {
            releaseDbResource(conn, stmt, rs);
        }
    }

    /**
     * 與不帶Ex的方法效果相同。不過會將異常拋出
     */
    public int executeCommandEx(String sql, Object[] params) throws Exception{
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DbManager.getInstnace().getConnection();
            stmt = conn.prepareStatement(sql);
            setParams(stmt, params);
            return stmt.executeUpdate();
        } catch (Exception e) {
            log.error(String.format("exception in sql =  %s  %s", sql, params != null ? Arrays.toString(params): ""),e);
            e.printStackTrace();
            throw e;
        } finally {
            releaseDbResource(conn, stmt, rs);
        }
    }
    
    public int[] executeBatchCommand(String sql, List<Object[]> paramsList) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DbManager.getInstnace().getConnection();
            conn.setAutoCommit(false);
            stmt = conn.prepareStatement(sql);
            for (int i = 0; i < paramsList.size(); i++) {
                setParams(stmt, paramsList.get(i));
                stmt.addBatch();
            }

            int[] effRows = stmt.executeBatch();
            conn.commit();
            return effRows;
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                log.error(String.format("exception in cmd = [ %s ] ", sql),
                        e1);
            }
            return null;
        } finally {
            releaseDbResource(conn, stmt, rs);
        }
    }

    public <T> List<T> executeQuery_ObjcetList(String cmd, Object[] params,
            ResultObjectBuilder<T> builder) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<T> list = new ArrayList<T>();
        try {
            conn = DbManager.getInstnace().getConnection();
            stmt = conn.prepareStatement(cmd);
            setParams(stmt, params);
            rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(builder.build(rs));
            }
            return list;
        } catch (Throwable t) {
            log.error(String.format("exception in cmd = [ %s ]", cmd), t);
            return null;
        } finally {
            releaseDbResource(conn, stmt, rs);
        }
    }
    
    public <T> T executeScalerObject(String cmd, Object[] params,
            ResultObjectBuilder<T> builder) throws Throwable {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DbManager.getInstnace().getConnection();
            stmt = conn.prepareStatement(cmd);
            setParams(stmt, params);
            rs = stmt.executeQuery();
            while (rs.next()) {
                return builder.build(rs);
            }
            return null;
        } catch (Throwable t) {
            log.error(String.format("exception in cmd = [ %s ] ", cmd), t);
            throw t;
        } finally {
            releaseDbResource(conn, stmt, rs);
        }
    }

    
    public void destroy() {
        try {
            this.dbNode.destroy();
        } catch (SQLException e) {
            e.printStackTrace();
            log.equals(e);
        }
    }
    
    private void setParams(PreparedStatement stmt, Object[] param)
            throws SQLException {
        if (param != null) {
            for (int i = 0; i < param.length; i++) {
                Object o = param[i];
                if (o instanceof Integer) {
                    stmt.setInt(i + 1, (Integer) o);
                } else if (o instanceof Short) {
                    stmt.setShort(i + 1, (Short) o);
                } else if (o instanceof Long) {
                    stmt.setLong(i + 1, (Long) o);
                } else if (o instanceof String) {
                    stmt.setString(i + 1, (String) o);
                } else if (o instanceof String) {
                    stmt.setString(i + 1, (String) o);
                } else if (o instanceof Date) {
                    stmt.setDate(i + 1, (Date) o);
                } else if (o instanceof Boolean) {
                    stmt.setBoolean(i + 1, (Boolean) o);
                } else if (o instanceof byte[]) {
                    stmt.setBytes(i + 1, (byte[]) o);
                } else if (o instanceof Byte) {
                    stmt.setByte(i + 1, (Byte) o);
                } else if (o instanceof Double) {
                    stmt.setDouble(i + 1, (Double) o);
                } else if (o instanceof Float) {
                    stmt.setFloat(i + 1, (Float) o);
                } else if (o == null) {
                    stmt.setFloat(i + 1, Types.OTHER);
                } else {
                    log.error(" params contains null value");
                }
            }
        }
    }

    public Connection getConnection() {
        try {
            return dbNode.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void releaseDbResource(Connection conn,
            PreparedStatement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
                rs = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (stmt != null) {
            try {
                stmt.close();
                stmt = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (conn != null) {
            try {
                conn.close();
                conn = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}

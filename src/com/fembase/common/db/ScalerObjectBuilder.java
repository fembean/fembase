package com.fembase.common.db;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class ScalerObjectBuilder {

    public static final ResultObjectBuilder<String> stringBuilder = new ResultObjectBuilder<String>() {
        
        @Override
        public String build(ResultSet rs) throws SQLException {
            return rs.getString(1);
        }
    };
    

    public static final ResultObjectBuilder<Integer> intBuilder = new ResultObjectBuilder<Integer>() {
        
        @Override
        public Integer build(ResultSet rs) throws SQLException {
            return rs.getInt(1);
        }
    };
    
    public static final ResultObjectBuilder<String[]> stringArrayBuilder = new ResultObjectBuilder<String[]>() {
		
		@Override
		public String[] build(ResultSet rs) throws SQLException {
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			String[] result = new String[columnCount];
			for(int i=1; i<= columnCount; i++) {
				result[i-1] = rs.getString(i);
			}
			return result;
		}
	};
}

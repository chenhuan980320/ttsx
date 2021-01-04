package com.ttsx.dao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * 源辰信息
 * @author navy
 * @date 2020年7月17日
 */
public class DBHelper{
	/*
	 * static { // 加载驱动 - 只需要在类第一次加载的时候执行一次 try {
	 * Class.forName(ReadConfig.getInstance().getProperty("driverClassName")); }
	 * catch (ClassNotFoundException e) { e.printStackTrace(); } }
	 */

	/**
	 * 获取连接的方法
	 * @return 获取到的连接
	 */
	private Connection getConnection() {
		Connection con = null;
		try {
			// con = DriverManager.getConnection(ReadConfig.getInstance().getProperty("url"), ReadConfig.getInstance());
			// 从数据库连接池中获取一个空闲的连接 -> 先获取DataSource
			Context context = new InitialContext();
			
			// 从命名目录接口中根据资源名查询资源，前面java:comp/env/是固定的，类似于协议
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/youxian");
			
			// 从连接池中获取一个空闲连接
			con = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return con;
	}

	/**
	 * 给预编译块语句中的占位符?赋值
	 * @param pstmt
	 * @param params 要执行的sql语句中对应占位符?的值，即按照?的顺序给定的值
	 */
	private void setParams(PreparedStatement pstmt, Object ... params) {
		if (params == null || params.length <= 0) { // 说明没有参数给我， 也就意味着执行的SQL语句中没有占位符?
			return;
		}

		for (int i = 0, len = params.length; i < len; i ++) {
			try {
				pstmt.setObject(i + 1, params[i]);
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("第 " + (i + 1) + " 个参数注值失败...");
			}
		}
	}

	/**
	 * 给预编译块语句中的占位符?赋值
	 * @param pstmt
	 * @param params 要执行的sql语句中对应占位符?的值，即按照?的顺序给定的值
	 */
	private void setParams(PreparedStatement pstmt, List<Object> params) {
		if (params == null || params.isEmpty()) { // 说明没有参数给我， 也就意味着执行的SQL语句中没有占位符?
			return;
		}

		for (int i = 0, len = params.size(); i < len; i ++) {
			try {
				pstmt.setObject(i + 1, params.get(i));
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("第 " + (i + 1) + " 个参数注值失败...");
			}
		}
	}

	/**
	 * 关闭资源的方法
	 * @param rs 要关闭的结果集
	 * @param pstmt 要关闭的预编译对象
	 * @param con 要关闭的连接
	 */
	private void close(ResultSet rs, PreparedStatement pstmt, Connection con) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 更新操作
	 * @param sql 要执行的更新语句，可以是insert、delete或update
	 * @param params 要执行的sql语句中对应占位符?的值，即按照?的顺序给定的值
	 * @return
	 */
	public int update(String sql, Object ... params) {  // 采用不定参数形式
		int result = -1;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = this.getConnection();

			pstmt= con.prepareStatement(sql); // 预编译执行语句
			this.setParams(pstmt, params); //  给预编译执行语句中的占位符赋值
			result = pstmt.executeUpdate(); // 执行更新
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(null, pstmt, con);
		}
		return result;
	}


	public int updates(List<String> sqls, List<List<Object>> params) {  // 采用不定参数形式
		int result = -1;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = this.getConnection();

			con.setAutoCommit(false); // 关闭自动事务提交

			for (int i = 0, len = sqls.size(); i < len; i ++) {
				pstmt= con.prepareStatement(sqls.get(i)); // 预编译执行语句
				this.setParams(pstmt, params.get(i)); //  给预编译执行语句中的占位符赋值
				result = pstmt.executeUpdate(); // 执行更新
			}

			con.commit(); // 提交事务
		} catch (SQLException e) {
			result = 0;
			try {
				con.rollback();  // 回滚事务
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				con.setAutoCommit(true); // 最终还是要开启自动事务提交
			} catch (SQLException e) {
				e.printStackTrace();
			}
			this.close(null, pstmt, con);
		}
		return result;
	}

	/**
	 * 更新操作
	 * @param sql 要执行的更新语句，可以是insert、delete或update
	 * @param params 要执行的sql语句中对应占位符?的值，即按照?的顺序给定的值
	 * @return
	 */
	public int update(String sql, List<Object> params) {  // 采用不定参数形式
		int result = -1;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = this.getConnection();

			pstmt= con.prepareStatement(sql); // 预编译执行语句
			this.setParams(pstmt, params); //  给预编译执行语句中的占位符赋值
			result = pstmt.executeUpdate(); // 执行更新
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(null, pstmt, con);
		}
		return result;
	}

	/**
	 * 获取结果集中所有列的类名
	 * @param rs 结果集对象
	 * @return 
	 * @throws SQLException 
	 */
	private String[] getColumnNames(ResultSet rs) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData(); // 获取结果集中的元数据
		int colCount = rsmd.getColumnCount(); // 获取结果集中列的数量
		String[] colNames = new String[colCount];
		for (int i = 1; i <= colCount; i ++) { // 循环获取结果集中列的名字
			colNames[i - 1] = rsmd.getColumnName(i).toLowerCase(); // 将列名改成小写后存到数组中
		}
		return colNames;
	}

	/**
	 * 查询
	 * @param sql 要执行的查询语句
	 * @param params 要执行的sql语句中对应占位符?的值，即按照?的顺序给定的值
	 * @return 满足条件的数据 每一条数据存到一个map中以列名为键，以对应列的值位置，然后将每一条数据即map对象存到list中
	 */
	public List<Map<String, Object>> finds(String sql, Object ... params) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = this.getConnection(); // 获取连接
			pstmt = con.prepareStatement(sql); // 预编译语句
			this.setParams(pstmt, params); // 给预编译语句中的占位符赋值
			rs = pstmt.executeQuery(); // 执行查询
			Map<String, Object> map = null;

			// 如果获取结果集中列的类名 -> 取到列名后我们存到一个数组中，便于后面的循环取值 -> 如何确定数组的大小?
			String[] colNames = this.getColumnNames(rs);

			Object obj = null; // 列的数据
			String colType = null; // 返回的这个列的数据的类型名称
			Blob blob = null;
			byte[] bt = null;
			while(rs.next()) { // 每次循环得到一行数据
				map = new HashMap<String, Object>();

				// 循环获取每一列的值，循环所有的列名，根据列名获取当前这一行这一列的值
				for (String colName : colNames) {
					// 首先我们不必获取返回的这个列的数据的类型是不是blob，若干是blob那么我们就转成字节数组将这个数据存到本地
					obj = rs.getObject(colName);

					if (obj == null) {
						map.put(colName, obj);
						continue;
					}

					// 获取这个列值对象的类型
					colType = obj.getClass().getSimpleName();
					if ("BLOB".equals(colType)) {
						// 用blob获取，然后转成字节数据
						blob = rs.getBlob(colName);
						bt = blob.getBytes(1, (int)blob.length());
						map.put(colName,  bt);
					} else {
						map.put(colName, obj);
					}
				}
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(rs, pstmt, con);
		}
		return list;
	}

	public List<Map<String, Object>> finds(String sql, List<Object> params) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = this.getConnection(); // 获取连接
			pstmt = con.prepareStatement(sql); // 预编译语句
			this.setParams(pstmt, params); // 给预编译语句中的占位符赋值
			rs = pstmt.executeQuery(); // 执行查询
			Map<String, Object> map = null;

			// 如果获取结果集中列的类名 -> 取到列名后我们存到一个数组中，便于后面的循环取值 -> 如何确定数组的大小?
			String[] colNames = this.getColumnNames(rs);

			Object obj = null; // 列的数据
			String colType = null; // 返回的这个列的数据的类型名称
			Blob blob = null;
			byte[] bt = null;
			while(rs.next()) { // 每次循环得到一行数据
				map = new HashMap<String, Object>();

				// 循环获取每一列的值，循环所有的列名，根据列名获取当前这一行这一列的值
				for (String colName : colNames) {
					// 首先我们不必获取返回的这个列的数据的类型是不是blob，若干是blob那么我们就转成字节数组将这个数据存到本地
					obj = rs.getObject(colName);

					if (obj == null) {
						map.put(colName, obj);
						continue;
					}

					// 获取这个列值对象的类型
					colType = obj.getClass().getSimpleName();
					if ("BLOB".equals(colType)) {
						// 用blob获取，然后转成字节数据
						blob = rs.getBlob(colName);
						bt = blob.getBytes(1, (int)blob.length());
						map.put(colName,  bt);
					} else {
						map.put(colName, obj);
					}
				}
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(rs, pstmt, con);
		}
		return list;
	}

	/**
	 * 查询
	 * @param sql 要执行的查询语句
	 * @param params 要执行的sql语句中对应占位符?的值，即按照?的顺序给定的值
	 * @return 满足条件的数据 每一条数据存到一个map中以列名为键，以对应列的值位置，然后将每一条数据即map对象存到list中
	 */
	public List<Map<String, String>> gets(String sql, Object ... params) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = this.getConnection(); // 获取连接
			pstmt = con.prepareStatement(sql); // 预编译语句
			this.setParams(pstmt, params); // 给预编译语句中的占位符赋值
			rs = pstmt.executeQuery(); // 执行查询
			Map<String, String> map = null;

			// 如果获取结果集中列的类名 -> 取到列名后我们存到一个数组中，便于后面的循环取值 -> 如何确定数组的大小?
			String[] colNames = this.getColumnNames(rs);
			while(rs.next()) { // 每次循环得到一行数据
				map = new HashMap<String, String>();

				// 循环获取每一列的值，循环所有的列名，根据列名获取当前这一行这一列的值
				for (String colName : colNames) {
					map.put(colName, rs.getString(colName));
				}
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(rs, pstmt, con);
		}
		return list;
	}

	public List<Map<String, String>> gets(String sql, List<Object> params) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = this.getConnection(); // 获取连接
			pstmt = con.prepareStatement(sql); // 预编译语句
			this.setParams(pstmt, params); // 给预编译语句中的占位符赋值
			rs = pstmt.executeQuery(); // 执行查询
			Map<String, String> map = null;

			// 如果获取结果集中列的类名 -> 取到列名后我们存到一个数组中，便于后面的循环取值 -> 如何确定数组的大小?
			String[] colNames = this.getColumnNames(rs);
			while(rs.next()) { // 每次循环得到一行数据
				map = new HashMap<String, String>();

				// 循环获取每一列的值，循环所有的列名，根据列名获取当前这一行这一列的值
				for (String colName : colNames) {
					map.put(colName, rs.getString(colName));
				}
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(rs, pstmt, con);
		}
		return list;
	}

	/**
	 * 查询单行
	 * @param sql 要执行的查询语句
	 * @param params 要执行的sql语句中对应占位符?的值，即按照?的顺序给定的值
	 * @return 满足条件的数据 每一条数据存到一个map中以列名为键，以对应列的值位置，然后将每一条数据即map对象存到list中
	 */
	public Map<String, Object> find(String sql, Object ... params) {
		Map<String, Object> map = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = this.getConnection(); // 获取连接
			pstmt = con.prepareStatement(sql); // 预编译语句
			this.setParams(pstmt, params); // 给预编译语句中的占位符赋值
			rs = pstmt.executeQuery(); // 执行查询

			// 如果获取结果集中列的类名 -> 取到列名后我们存到一个数组中，便于后面的循环取值 -> 如何确定数组的大小?
			String[] colNames = this.getColumnNames(rs);

			Object obj = null; // 列的数据
			String colType = null; // 返回的这个列的数据的类型名称
			Blob blob = null;
			byte[] bt = null;

			if(rs.next()) { // 每次循环得到一行数据
				map = new HashMap<String, Object>();

				// 循环获取每一列的值，循环所有的列名，根据列名获取当前这一行这一列的值
				for (String colName : colNames) {
					// 首先我们不必获取返回的这个列的数据的类型是不是blob，若干是blob那么我们就转成字节数组将这个数据存到本地
					obj = rs.getObject(colName);

					if (obj == null) {
						map.put(colName, obj);
						continue;
					}

					// 获取这个列值对象的类型
					colType = obj.getClass().getSimpleName();
					if ("BLOB".equals(colType)) {
						// 用blob获取，然后转成字节数据
						blob = rs.getBlob(colName);
						bt = blob.getBytes(1, (int)blob.length());
						map.put(colName,  bt);
					} else {
						map.put(colName, obj);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(rs, pstmt, con);
		}
		return map;
	}

	public Map<String, Object> find(String sql, List<Object> params) {
		Map<String, Object> map = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = this.getConnection(); // 获取连接
			pstmt = con.prepareStatement(sql); // 预编译语句
			this.setParams(pstmt, params); // 给预编译语句中的占位符赋值
			rs = pstmt.executeQuery(); // 执行查询

			// 如果获取结果集中列的类名 -> 取到列名后我们存到一个数组中，便于后面的循环取值 -> 如何确定数组的大小?
			String[] colNames = this.getColumnNames(rs);

			Object obj = null; // 列的数据
			String colType = null; // 返回的这个列的数据的类型名称
			Blob blob = null;
			byte[] bt = null;

			if(rs.next()) { // 每次循环得到一行数据
				map = new HashMap<String, Object>();

				// 循环获取每一列的值，循环所有的列名，根据列名获取当前这一行这一列的值
				for (String colName : colNames) {
					// 首先我们不必获取返回的这个列的数据的类型是不是blob，若干是blob那么我们就转成字节数组将这个数据存到本地
					obj = rs.getObject(colName);

					if (obj == null) {
						map.put(colName, obj);
						continue;
					}

					// 获取这个列值对象的类型
					colType = obj.getClass().getSimpleName();
					if ("BLOB".equals(colType)) {
						// 用blob获取，然后转成字节数据
						blob = rs.getBlob(colName);
						bt = blob.getBytes(1, (int)blob.length());
						map.put(colName,  bt);
					} else {
						map.put(colName, obj);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(rs, pstmt, con);
		}
		return map;
	}

	/**
	 * 查询单行
	 * @param sql 要执行的查询语句
	 * @param params 要执行的sql语句中对应占位符?的值，即按照?的顺序给定的值
	 * @return 满足条件的数据 每一条数据存到一个map中以列名为键，以对应列的值位置，然后将每一条数据即map对象存到list中
	 */
	public Map<String, String> get(String sql, Object ... params) {
		Map<String, String> map = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = this.getConnection(); // 获取连接
			pstmt = con.prepareStatement(sql); // 预编译语句
			this.setParams(pstmt, params); // 给预编译语句中的占位符赋值
			rs = pstmt.executeQuery(); // 执行查询


			// 如果获取结果集中列的类名 -> 取到列名后我们存到一个数组中，便于后面的循环取值 -> 如何确定数组的大小?
			String[] colNames = this.getColumnNames(rs);
			if(rs.next()) { // 每次循环得到一行数据
				map = new HashMap<String, String>();

				// 循环获取每一列的值，循环所有的列名，根据列名获取当前这一行这一列的值
				for (String colName : colNames) {
					map.put(colName, rs.getString(colName));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(rs, pstmt, con);
		}
		return map;
	}

	public Map<String, String> get(String sql, List<Object> params) {
		Map<String, String> map = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = this.getConnection(); // 获取连接
			pstmt = con.prepareStatement(sql); // 预编译语句
			this.setParams(pstmt, params); // 给预编译语句中的占位符赋值
			rs = pstmt.executeQuery(); // 执行查询


			// 如果获取结果集中列的类名 -> 取到列名后我们存到一个数组中，便于后面的循环取值 -> 如何确定数组的大小?
			String[] colNames = this.getColumnNames(rs);
			if(rs.next()) { // 每次循环得到一行数据
				map = new HashMap<String, String>();

				// 循环获取每一列的值，循环所有的列名，根据列名获取当前这一行这一列的值
				for (String colName : colNames) {
					map.put(colName, rs.getString(colName));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(rs, pstmt, con);
		}
		return map;
	}

	/**
	 * 获取总记录数的方法  一行一列
	 * @param sql 要执行的查询语句
	 * @param params 要执行的sql语句中对应占位符?的值，即按照?的顺序给定的值
	 * @return 总记录数
	 */
	public int total(String sql, Object ... params) {
		int result = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = this.getConnection(); // 获取连接
			pstmt = con.prepareStatement(sql); // 预编译语句
			this.setParams(pstmt, params); // 给预编译语句中的占位符赋值
			rs = pstmt.executeQuery(); // 执行查询
			if(rs.next()) { // 每次循环得到一行数据
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(rs, pstmt, con);
		}
		return result;
	}

	public int total(String sql, List<Object> params) {
		int result = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = this.getConnection(); // 获取连接
			pstmt = con.prepareStatement(sql); // 预编译语句
			this.setParams(pstmt, params); // 给预编译语句中的占位符赋值
			rs = pstmt.executeQuery(); // 执行查询
			if(rs.next()) { // 每次循环得到一行数据
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(rs, pstmt, con);
		}
		return result;
	}

	/**
	 * 获取指定类中的所有setter方法
	 * @param c
	 * @return 以方法名全部字母小写为键，以对应的方法为值
	 */
	private Map<String, Method> getSetters(Class<?> c) {
		Map<String, Method> setters = new HashMap<String, Method>();

		// 先获取这个类中的所有方法，然后提取出setter方法
		Method[] methods = c.getMethods();

		String methodName = null;
		for (Method md : methods) {
			methodName = md.getName().toLowerCase(); // 将方法名所有字母转成小写
			if (methodName.startsWith("set")) { // 说明是一个setter方法
				setters.put(methodName, md);
			}
		}
		return setters;
	}

	/**
	 * 基于实体类对象的查询
	 * @param <T> 泛型  参数化类型，即类型的参数化
	 * @param c 这个查询语句查询出来的结果要注入的实体类
	 * @param sql 要执行的查询语句
	 * @param params 查询语句中对应占位符?的值
	 * @return
	 */
	public <T> List<T> finds(Class<T> c, String sql, Object ... params) {
		List<T> list = new ArrayList<T>();
		Connection con= null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = this.getConnection(); // 获取链接
			pstmt = con.prepareStatement(sql); // 预编译执行语句块
			this.setParams(pstmt, params); // 给预编译执行语句中的占位符赋值

			rs = pstmt.executeQuery(); // 执行查询获取结果集

			// 获取结果集中所有的列的类名
			String[] colNames = this.getColumnNames(rs);

			// 获取指定类中的所有setter方法
			Map<String, Method> setters = this.getSetters(c);

			// 将结果集中的数据注入到实体类对象中
			T t;
			String methodName = null;
			Method method = null;
			Class<?>[] types = null;
			Class<?> type = null;

			// 循环获取对象
			while(rs.next()) {
				// 先实例化一个对象
				t = c.newInstance(); // 每循环一次就是一个实体类对象，所有每次循环我都需要创建一个对象  new StuInfo();

				// 将这一行数据中每一列的值注入到对应的属性中
				for (String colName: colNames) { // 循环所有列，将对应的值注入到对应的实体类属性中
					methodName = "set" + colName; // setsid  setsname

					// 根据方法名查找对应的setter方法
					method = setters.getOrDefault(methodName, null);
					
					if (method == null) {
						continue;
					}

					// 因为java中允许方法重载，所以我们在调用方法的时候必须确定形参的类型，所以我需要获取这个方法的形参列表
					types = method.getParameterTypes();

					if (types == null || types.length <= 0) { // 说明没有形参，那么我这个值也没法注入
						continue; // 则结束本次循环，开始下个循环
					}

					// 如果有形参，按照规范，我们的setter方法里面只会有一个参数，所有我直接去第一个参数的类型
					type = types[0];

					// 接下来判断形参类型
					if (Integer.TYPE == type || Integer.class == type) { // 说明要的是一个整形值或一个整形对象
						// 反向激活这个方法，第一个参数是这个方法是哪个对象的，第二个参数是要注入的值，即这个方法的形参对应的实参值
						method.invoke(t, rs.getInt(colName)); // obj.setSid(args) -> stu.setSid(101)
					} else if (Float.TYPE == type || Float.class == type) {
						method.invoke(t, rs.getFloat(colName));
					} else if (Double.TYPE == type || Double.class == type) {
						method.invoke(t, rs.getDouble(colName));
					} else {
						method.invoke(t, rs.getString(colName));
					}
				}

				// 循环结束，说明所有的属性已经注值完成，即这个对象已经构成完成，则将这个对象存到集合中
				list.add(t);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} finally { // 关闭资源
			this.close(rs, pstmt, con);
		}
		return list;
	}

	/**
	 * 基于实体类对象的查询
	 * @param <T> 泛型  参数化类型，即类型的参数化
	 * @param c 这个查询语句查询出来的结果要注入的实体类
	 * @param sql 要执行的查询语句
	 * @param params 查询语句中对应占位符?的值
	 * @return
	 */
	public <T> List<T> finds(Class<T> c, String sql, List<Object> params) {
		List<T> list = new ArrayList<T>();
		Connection con= null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = this.getConnection(); // 获取链接
			pstmt = con.prepareStatement(sql); // 预编译执行语句块
			this.setParams(pstmt, params); // 给预编译执行语句中的占位符赋值

			rs = pstmt.executeQuery(); // 执行查询获取结果集

			// 获取结果集中所有的列的类名
			String[] colNames = this.getColumnNames(rs);

			// 获取指定类中的所有setter方法
			Map<String, Method> setters = this.getSetters(c);

			// 将结果集中的数据注入到实体类对象中
			T t;
			String methodName = null;
			Method method = null;
			Class<?>[] types = null;
			Class<?> type = null;

			// 循环获取对象
			while(rs.next()) {
				// 先实例化一个对象
				t = c.newInstance(); // 每循环一次就是一个实体类对象，所有每次循环我都需要创建一个对象  new StuInfo();

				// 将这一行数据中每一列的值注入到对应的属性中
				for (String colName: colNames) { // 循环所有列，将对应的值注入到对应的实体类属性中
					methodName = "set" + colName; // setsid  setsname

					// 根据方法名查找对应的setter方法
					method = setters.getOrDefault(methodName, null);
					
					if (method == null) {
						continue;
					}

					// 因为java中允许方法重载，所以我们在调用方法的时候必须确定形参的类型，所以我需要获取这个方法的形参列表
					types = method.getParameterTypes();

					if (types == null || types.length <= 0) { // 说明没有形参，那么我这个值也没法注入
						continue; // 则结束本次循环，开始下个循环
					}

					// 如果有形参，按照规范，我们的setter方法里面只会有一个参数，所有我直接去第一个参数的类型
					type = types[0];

					// 接下来判断形参类型
					if (Integer.TYPE == type || Integer.class == type) { // 说明要的是一个整形值或一个整形对象
						// 反向激活这个方法，第一个参数是这个方法是哪个对象的，第二个参数是要注入的值，即这个方法的形参对应的实参值
						method.invoke(t, rs.getInt(colName)); // obj.setSid(args) -> stu.setSid(101)
					} else if (Float.TYPE == type || Float.class == type) {
						method.invoke(t, rs.getFloat(colName));
					} else if (Double.TYPE == type || Double.class == type) {
						method.invoke(t, rs.getDouble(colName));
					} else {
						method.invoke(t, rs.getString(colName));
					}
				}

				// 循环结束，说明所有的属性已经注值完成，即这个对象已经构成完成，则将这个对象存到集合中
				list.add(t);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} finally { // 关闭资源
			this.close(rs, pstmt, con);
		}
		return list;
	}

	/**
	 * 基于实体类对象的查询
	 * @param <T> 泛型  参数化类型，即类型的参数化
	 * @param c 这个查询语句查询出来的结果要注入的实体类
	 * @param sql 要执行的查询语句
	 * @param params 查询语句中对应占位符?的值
	 * @return
	 */
	public <T> T find(Class<T> c, String sql, Object ... params) {
		Connection con= null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// 将结果集中的数据注入到实体类对象中
		T t = null;
		
		try {
			con = this.getConnection(); // 获取链接
			pstmt = con.prepareStatement(sql); // 预编译执行语句块
			this.setParams(pstmt, params); // 给预编译执行语句中的占位符赋值

			rs = pstmt.executeQuery(); // 执行查询获取结果集

			// 获取结果集中所有的列的类名
			String[] colNames = this.getColumnNames(rs);

			// 获取指定类中的所有setter方法
			Map<String, Method> setters = this.getSetters(c);


			String methodName = null;
			Method method = null;
			Class<?>[] types = null;
			Class<?> type = null;

			// 循环获取对象
			if(rs.next()) {
				// 先实例化一个对象
				t = c.newInstance(); // 每循环一次就是一个实体类对象，所有每次循环我都需要创建一个对象  new StuInfo();

				// 将这一行数据中每一列的值注入到对应的属性中
				for (String colName: colNames) { // 循环所有列，将对应的值注入到对应的实体类属性中
					methodName = "set" + colName; // setsid  setsname

					// 根据方法名查找对应的setter方法
					method = setters.getOrDefault(methodName, null);
					
					if (method == null) {
						continue;
					}

					// 因为java中允许方法重载，所以我们在调用方法的时候必须确定形参的类型，所以我需要获取这个方法的形参列表
					types = method.getParameterTypes();

					if (types == null || types.length <= 0) { // 说明没有形参，那么我这个值也没法注入
						continue; // 则结束本次循环，开始下个循环
					}

					// 如果有形参，按照规范，我们的setter方法里面只会有一个参数，所有我直接去第一个参数的类型
					type = types[0];

					// 接下来判断形参类型
					if (Integer.TYPE == type || Integer.class == type) { // 说明要的是一个整形值或一个整形对象
						// 反向激活这个方法，第一个参数是这个方法是哪个对象的，第二个参数是要注入的值，即这个方法的形参对应的实参值
						method.invoke(t, rs.getInt(colName)); // obj.setSid(args) -> stu.setSid(101)
					} else if (Float.TYPE == type || Float.class == type) {
						method.invoke(t, rs.getFloat(colName));
					} else if (Double.TYPE == type || Double.class == type) {
						method.invoke(t, rs.getDouble(colName));
					} else {
						method.invoke(t, rs.getString(colName));
					}
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} finally { // 关闭资源
			this.close(rs, pstmt, con);
		}
		return t;
	}
	
	/**
	 * 基于实体类对象的查询
	 * @param <T> 泛型  参数化类型，即类型的参数化
	 * @param c 这个查询语句查询出来的结果要注入的实体类
	 * @param sql 要执行的查询语句
	 * @param params 查询语句中对应占位符?的值
	 * @return
	 */
	public <T> T find(Class<T> c, String sql, List<Object> params) {
		Connection con= null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		T t = null;
		try {
			con = this.getConnection(); // 获取链接
			pstmt = con.prepareStatement(sql); // 预编译执行语句块
			this.setParams(pstmt, params); // 给预编译执行语句中的占位符赋值

			rs = pstmt.executeQuery(); // 执行查询获取结果集

			// 获取结果集中所有的列的类名
			String[] colNames = this.getColumnNames(rs);

			// 获取指定类中的所有setter方法
			Map<String, Method> setters = this.getSetters(c);

			// 将结果集中的数据注入到实体类对象中
			String methodName = null;
			Method method = null;
			Class<?>[] types = null;
			Class<?> type = null;

			// 循环获取对象
			if(rs.next()) {
				// 先实例化一个对象
				t = c.newInstance(); // 每循环一次就是一个实体类对象，所有每次循环我都需要创建一个对象  new StuInfo();

				// 将这一行数据中每一列的值注入到对应的属性中
				for (String colName: colNames) { // 循环所有列，将对应的值注入到对应的实体类属性中
					methodName = "set" + colName; // setsid  setsname

					// 根据方法名查找对应的setter方法
					method = setters.getOrDefault(methodName, null);
					
					if (method == null) {
						continue;
					}
					
					// 因为java中允许方法重载，所以我们在调用方法的时候必须确定形参的类型，所以我需要获取这个方法的形参列表
					types = method.getParameterTypes();

					if (types == null || types.length <= 0) { // 说明没有形参，那么我这个值也没法注入
						continue; // 则结束本次循环，开始下个循环
					}

					// 如果有形参，按照规范，我们的setter方法里面只会有一个参数，所有我直接去第一个参数的类型
					type = types[0];

					// 接下来判断形参类型
					if (Integer.TYPE == type || Integer.class == type) { // 说明要的是一个整形值或一个整形对象
						// 反向激活这个方法，第一个参数是这个方法是哪个对象的，第二个参数是要注入的值，即这个方法的形参对应的实参值
						method.invoke(t, rs.getInt(colName)); // obj.setSid(args) -> stu.setSid(101)
					} else if (Float.TYPE == type || Float.class == type) {
						method.invoke(t, rs.getFloat(colName));
					} else if (Double.TYPE == type || Double.class == type) {
						method.invoke(t, rs.getDouble(colName));
					} else {
						method.invoke(t, rs.getString(colName));
					}
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} finally { // 关闭资源
			this.close(rs, pstmt, con);
		}
		return t;
	}
}

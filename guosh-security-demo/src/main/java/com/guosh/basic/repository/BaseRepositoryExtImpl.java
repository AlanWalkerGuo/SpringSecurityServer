/**
 * 
 */
package com.guosh.basic.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import java.util.List;
import java.util.Map;

/**
 * @author guoshaohua
 *
 */
public abstract class BaseRepositoryExtImpl implements BaseRepositoryExt {
	
	@Autowired
	protected SqlMap sqlMap;
	@Autowired
	protected JdbcTemplate jdbcTemplate;

	
	protected DbType dbType = DbType.Mysql;

	public void setDbType(DbType dbType) {
		this.dbType = dbType;
	}

	/**
	 * 获取配置文件中的动态SQL语句.
	 * @param sqlName 配置的SQL名称（key）
	 * @return 动态SQL语句.
	 */
	public String _(String sqlName) {
		return sqlMap.getSqls().get(sqlName);
	}

	/**
	 * 处理动态sql.
	 * 
	 * @param params
	 *            参数.
	 * @param name
	 *            动态Sql的名称.
	 * @return
	 * @throws Exception
	 */
	public ISqlElement processSql(Map<String, Object> params, String name) {
		ISqlElement rs = DynamicSqlUtil.processSql(params, _(name));
		return rs;
	}
	
	/**
	 * 查找分页数据
	 * @param querySqlName 查询语句名称
	 * @param countSqlName 总数语句名称
	 * @param params 数据参数
	 * @param pageable 分页参数
	 * @return 分页对象
	 */
	public Page<Map<String, Object>> findPage(String querySqlName, String countSqlName, Map<String, Object> params, Pageable pageable) {
		return findPage(querySqlName, countSqlName, params, pageable, null, dbType);
	}
	
	/**
	 * 查找分页数据
	 * @param querySqlName 查询语句名称
	 * @param countSqlName 总数语句名称
	 * @param params 数据参数
	 * @param pageable 分页参数
	 * @param rowMapper 行映射
	 * @return 分页对象
	 */
	public <T> Page<T> findPage(String querySqlName, String countSqlName, Map<String, Object> params, Pageable pageable, RowMapper<T> rowMapper) {
		return findPage(querySqlName, countSqlName, params, pageable, rowMapper, dbType);
	}
	
	/**
	 * 查找分页数据
	 * @param querySqlName 查询语句名称
	 * @param countSqlName 总数语句名称
	 * @param params 数据参数
	 * @param pageable 分页参数
	 * @param clazz 分页内容中的数据类型
	 * @return 分页对象
	 */
	@SuppressWarnings("unchecked")
	public <T> Page<T> findPage(String querySqlName, String countSqlName, Map<String, Object> params, Pageable pageable, Class<?> clazz) {
		return (Page<T>) findPage(querySqlName, countSqlName, params, pageable, new ObjectRowMapper<>(clazz));
	}
	
	/**
	 * 查找分页数据
	 * @param querySqlName 查询语句名称
	 * @param countSqlName 总数语句名称
	 * @param params 数据参数
	 * @param pageable 分页参数
	 * @param clazz 分页内容中的数据类型
	 * @param dbType 数据库类型，默认为Mysql
	 * @return 分页对象
	 */
	@SuppressWarnings("unchecked")
	public <T> Page<T> findPageObj(String querySqlName, String countSqlName, Map<String, Object> params, Pageable pageable, Class<?> clazz, DbType dbType) {
		return (Page<T>) findPage(querySqlName, countSqlName, params, pageable, new ObjectRowMapper<>(clazz), dbType);
	}
	
	/**
	 * 查找分页数据
	 * @param querySqlName 查询语句名称
	 * @param countSqlName 总数语句名称
	 * @param params 数据参数
	 * @param pageable 分页参数
	 * @param rowMapper 行映射，如果为<code>null</code>则返回的对象为<code>Page&lt;Map&lt;String, Object&gt;&gt;</code>类型
	 * @param dbType 数据库类型，默认为Mysql
	 * @return 分页对象
	 */
	@SuppressWarnings("unchecked")
	public <T> Page<T> findPage(String querySqlName, String countSqlName, Map<String, Object> params, Pageable pageable, RowMapper<T> rowMapper, DbType dbType) {
		//Long a=new Date().getTime();
		ISqlElement seQry = processSql(params, querySqlName);
		ISqlElement seCount = processSql(params, countSqlName);
		//Long b=new Date().getTime();
		//System.out.println("模板解析耗时》》》》》"+(b-a)+"ms");
		Long totalCount = jdbcTemplate.queryForObject(seCount.getSql(), seCount.getParams(), Long.class);
		String sqlQry = null;
		switch (dbType) {
		case Oracle:
			int offset1 = pageable.getOffset();
			int offset2 = offset1 + pageable.getPageSize();
			StringBuffer pageSql = new StringBuffer(500)
					.append("SELECT * FROM (SELECT PAGETABLE01.*, ROWNUM RN  FROM (")
					.append(seQry.getSql())
					.append(") PAGETABLE01 WHERE ROWNUM <= ").append(offset2).append(") WHERE RN > ").append(offset1);
			sqlQry = pageSql.toString();
			break;
		case SqlServer2012:
			sqlQry = seQry.getSql() + " OFFSET "+pageable.getOffset()+" ROWS FETCH NEXT "+pageable.getPageSize()+" ROWS ONLY";
			break;
		default: // mysql
			sqlQry = seQry.getSql() + " LIMIT "+pageable.getOffset()+","+pageable.getPageSize();
			break;
		}
		List<T> content = rowMapper != null ? jdbcTemplate.query(sqlQry, seQry.getParams(), rowMapper) : (List<T>)jdbcTemplate.queryForList(sqlQry, seQry.getParams());
		//System.out.println("查询数据耗时》》》》》"+(new Date().getTime()-b)+"ms");
		return new PageImpl<>(content, pageable, totalCount);
	}

	/**
	 * 查询列表数据
	 * @param querySqlName 查询语句名称
	 * @param params 数据参数
	 * @param rowMapper 行映射，如果为<code>null</code>则返回的对象为<code>List&lt;Map&lt;String, Object&gt;&gt;</code>类型
	 * @return 数据列表
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> findList(String querySqlName, Map<String, Object> params, RowMapper<T> rowMapper) {
		ISqlElement seQry = processSql(params, querySqlName);
		List<T> content = rowMapper != null ? jdbcTemplate.query(seQry.getSql(), seQry.getParams(), rowMapper) : (List<T>)jdbcTemplate.queryForList(seQry.getSql(), seQry.getParams());
		return content;
	}
	
	/**查询列表数据
	 * @param querySqlName 查询语句名称
	 * @param params 数据参数
	 * @return 数据列表
	 */
	public List<Map<String, Object>> findList(String querySqlName, Map<String, Object> params) {
		return findList(querySqlName, params, null);
	}

	/**
	 * 查询列表数据
	 * @param querySqlName 查询语句名称
	 * @param params 数据参数
	 * @param clazz 列表中的数据类型
	 * @return 数据列表
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> findListObj(String querySqlName, Map<String, Object> params, Class<?> clazz) {
		return (List<T>)findList(querySqlName, params, new ObjectRowMapper<>(clazz));
	}
	
	/**
	 * 查询一行数据并转换为对象
	 * @param querySqlName 查询语句名称
	 * @param params 数据参数
	 * @param clazz 列表中的数据类型
	 * @return 一行数据的对象
	 */
	public <T> T findOneObj(String querySqlName, Map<String, Object> params, Class<?> clazz) {
		List<T> list = findListObj(querySqlName, params, clazz);
		return list.size() > 0 ? list.get(0) : null;
	}
	
	/**
	 * 查询一行数据并转换为Map
	 * @param querySqlName 查询语句名称
	 * @param params 数据参数
	 * @return 一行数据的Map
	 */
	public Map<String, Object> findOneMap(String querySqlName, Map<String, Object> params) {
		List<Map<String,Object>> list = findList(querySqlName, params);
		return list.size() > 0 ? list.get(0) : null;
	}
	
	
	

}

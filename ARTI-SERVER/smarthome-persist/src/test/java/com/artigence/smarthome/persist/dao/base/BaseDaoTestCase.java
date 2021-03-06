package com.artigence.smarthome.persist.dao.base;



import javax.sql.DataSource;

import org.junit.Ignore;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;

import com.artigence.smarthome.persist.operate.DBUnitUtils;
import static junit.framework.Assert.*;

/**
 * 本基类主要为子类指定好要装载的spring配置文件
 * 及在运行测试前通过dbunit插入测试数据在数据库中,运行完测试删除测试数据 
 *
 * @author badqiu
 * 请设置好要装载的spring配置文件,一般开发数据库与测试数据库分开
 * 所以你要装载的资源文件应改为"classpath:/spring/*-test-resource.xml"
 */
@ContextConfiguration(locations={"classpath:/spring-test.xml"})
@Ignore
public class BaseDaoTestCase extends AbstractTransactionalJUnit4SpringContextTests  {
	protected DBUnitUtils dbUnitUtils = new DBUnitUtils();

	protected DataSource getDataSource() {
		DataSource ds = (DataSource)applicationContext.getBean("dataSource");
		assertNotNull("not found 'dataSource'",ds);
		return ds;
	}
	
	@BeforeTransaction
	public void onSetUpBeforeTransaction() throws Exception {
		String jdbcSchema = null;  // set schema for oracle
		dbUnitUtils.setDataSource(getDataSource(),jdbcSchema);
		dbUnitUtils.insertDbunitTestDatas(getDbUnitDataFiles());
	}
	
	@AfterTransaction
	public void onTearDownAfterTransaction() throws Exception {
		System.out.println("[DbUnit INFO] delete test datas");
		dbUnitUtils.deleteTestDatas();
	}

	/** 得到要加载的dbunit文件 */
	protected String[] getDbUnitDataFiles() {
		return new String[]{};
	}
	
}

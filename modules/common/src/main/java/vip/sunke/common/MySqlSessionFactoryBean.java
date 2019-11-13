package vip.sunke.common;


import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;

import java.io.IOException;

/**
 * @author sunke
 * @Date 2017/9/20 11:28
 * @description
 */

public class MySqlSessionFactoryBean extends SqlSessionFactoryBean {


    @Override
    protected SqlSessionFactory buildSqlSessionFactory() throws IOException {
        try {
            return super.buildSqlSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}

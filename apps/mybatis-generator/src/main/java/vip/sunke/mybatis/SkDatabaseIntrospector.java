package vip.sunke.mybatis;

import org.mybatis.generator.api.JavaTypeResolver;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.internal.db.DatabaseIntrospector;

import java.sql.DatabaseMetaData;
import java.util.List;

/**
 * @author sunke
 * @Date 2019-05-08 14:44
 * @description
 */

public class SkDatabaseIntrospector extends DatabaseIntrospector {

    public SkDatabaseIntrospector(Context context, DatabaseMetaData databaseMetaData, JavaTypeResolver javaTypeResolver, List<String> warnings) {
        super(context, databaseMetaData, javaTypeResolver, warnings);
    }


}

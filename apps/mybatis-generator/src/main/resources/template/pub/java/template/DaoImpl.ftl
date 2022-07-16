package ${package};

import ${modelExtClass};
import ${modelSearchExtClass};
import ${abstractBaseDaoClass};
import ${mapperExtClass};
import ${iDaoClass};
import ${iBaseMapperClass};

import org.springframework.stereotype.Repository;
import javax.annotation.Resource;

/**
 *    @author ${author}
 *    @Date ${date}
 *    @description ${description}
 */
@Repository(value = "${daoVar}")
public class ${daoImpl} extends ${abstractBaseDao}<${modelExt}, ${modelSearchExt},${keyType}> implements ${iDao} {

    @Resource
    private ${mapperExt} ${mapperExtVar};

    @Override
    public IBaseMapper<${modelExt},  ${modelSearchExt},${keyType}> getMapper() {
        return ${mapperExtVar};
    }
}
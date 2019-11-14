package ${package};

import ${modelExtClass};
import ${modelSearchClass};
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
public class ${daoImpl} extends ${abstractBaseDao}<${modelExt}, ${modelSearch},String> implements ${iDao} {

    @Resource(name = "${mapperExtVar}")
    private ${mapperExt} ${mapperExtVar};

    @Override
    public IBaseMapper<${modelExt},  ${modelSearch},String> getMapper() {
        return ${mapperExtVar};
    }
}
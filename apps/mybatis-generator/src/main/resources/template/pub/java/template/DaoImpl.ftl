package ${package};

import ${modelExtClass};
import ${searchClass};
import ${abstractBaseDao};
import ${mapperExtClass};
import ${iDao};
import ${baseMapper};
import org.springframework.stereotype.Repository;
import javax.annotation.Resource;

/**
 *    @author ${author}
 *    @Date ${date}
 *    @description ${description}
 */
@Repository(value = "${repositoryValue}")
public class ${shortDaoImplClass} extends ${shortAbstractBaseDao}<${shortModelExtClass}, ${shortSearchClass},${keyType}> implements ${shortIDao} {

    @Resource(name = "${shortMapperExtName}")
    private ${shortMapperExt} ${shortMapperExtName};

    @Override
    public IBaseMapper<${shortModelExtClass},  ${shortSearchClass},${keyType}> getMapper() {
        return ${shortMapperExtName};
    }
}
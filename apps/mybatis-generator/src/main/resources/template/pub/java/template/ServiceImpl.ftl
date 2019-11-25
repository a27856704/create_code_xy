package ${package};

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import ${modelExtClass};
import ${iDao};
import ${modelSearchClass};
import ${iBaseDaoClass};
import ${abstractBaseServiceClass};
import ${serviceImplClass};


/**
*    @author ${author}
*    @Date ${date}
*    @description ${description}
*/
@Service(value = "${serviceVar}")
public class ${serviceImpl} extends ${abstractBaseService}<${modelExt}, ${modelSearch},String> implements ${iService} {

    @Resource(name = "${daoVar}")
    private ${iDao} ${daoVar};

    @Override
    public IBaseDao<${modelExt}, ${modelSearch},String> getDao() {
        return ${daoVar};
    }
}
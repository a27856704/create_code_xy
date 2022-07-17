package ${package};

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import ${modelExtClass};
import ${iDaoClass};
import ${modelSearchExtClass};
import ${iBaseDaoClass};
import ${abstractBaseServiceClass};
import ${iServiceClass};



/**
*    @author ${author}
*    @Date ${date}
*    @description ${description}
*/
@Service(value = "${serviceVar}")
public class ${serviceImpl} extends ${abstractBaseService}<${modelExt}, ${modelSearchExt},${keyType}> implements ${iService} {

    @Resource(name = "${daoVar}")
    private ${iDao} ${daoVar};

    @Override
    public IBaseDao<${modelExt}, ${modelSearchExt},${keyType}> getDao() {
        return ${daoVar};
    }
}
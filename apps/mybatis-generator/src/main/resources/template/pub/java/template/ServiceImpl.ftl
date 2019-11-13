package ${package};

import ${modelExtClass};
import ${iDao};
import ${searchClass};
import ${baseDaoClass};
import ${abstractBaseService};
import ${iService};
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
*    @author ${author}
*    @Date ${date}
*    @description ${description}
*/
@Service(value = "${serviceValue}")
public class ${shortServiceImplClass} extends ${shortAbstractBaseService}<${shortModelExtClass}, ${shortSearchClass},${keyType}> implements ${shortIService} {

    @Resource(name = "${repositoryValue}")
    private ${shortIDao} ${repositoryValue};

    @Override
    public IBaseDao<${shortModelExtClass}, ${shortSearchClass},${keyType}> getDao() {
        return ${repositoryValue};
    }
}
package ${package};
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RestController;
import ${pubInterPackage}.exception.SkException;
import ${iBaseServiceClass};
import ${restfulControllerClass};
import ${modelDTOClass};
import ${modelExtClass};
import ${modelSearchClass};
import ${iServiceClass};
import ${detailVOClass};
import ${listVOClass};
import ${modelVOClass};

/**
*    @author ${author}
*    @Date ${date}
*    @description ${description}
*/
@Api(tags = "${apiTags}", description = "${apiDesc}")
@RestController(value ="${controllerName}")
@RequestMapping("${route}")
public class ${controllerClass} extends RestfulController<${modelDTO},${modelExt}, ${modelSearch},${modelVO},${detailVO},${listVO}> {

	@Resource(name = "${serviceVar}")
	private ${iService} ${serviceVar};

	@Override
	public IBaseService<${modelExt}, ${modelSearch},String> getBaseService() throws SkException {
		return ${serviceVar};
	}

	@Override
	public String getBaseRoute() {
		return "${route}";
	}

}
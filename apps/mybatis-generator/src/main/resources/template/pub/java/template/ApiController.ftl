package ${package};

import ${baseServiceClass};
import ${pubInterPackage}.exception.SkException;
import ${modelExtClass};
import ${searchClass};
import ${iService};
import ${modelDTOClass};
import ${modelVOClass};
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RestController;

/**
*    @author ${author}
*    @Date ${date}
*    @description ${description}
*/

@RestController(value ="${controllerName}")
@RequestMapping("${route}")
public class ${controllerClass} extends ApiManageController<${dto},${shortModelExtClass}, ${shortSearchClass},${keyType},${vo}> {

	@Resource(name = "${serviceValue}")
	private ${shortServiceClass} ${serviceValue};

	@Override
	public IBaseService<${shortModelExtClass}, ${shortSearchClass},${keyType}> getBaseService() throws SkException {
		return ${serviceValue};
	}

	@Override
	public String getBaseRoute() {
		return "${route}";
	}

	@Override
	public String getBaseView() {
		return "${baseView}";
	}

	@Override
	public String getCurrentMenu() {
		return "${currentMenu}";
	}

	@Override
	public String getMenuModel() {
		return "${menuModel}";
	}

}
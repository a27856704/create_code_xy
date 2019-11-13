package ${package};

import ${baseServiceClass};
import ${pubInterPackage}.exception.SkException;
import ${modelExtClass};
import ${searchClass};
import ${iService};
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;

/**
*    @author ${author}
*    @Date ${date}
*    @description ${description}
*/

@Controller(value ="${controllerName}")
@RequestMapping("${route}")
public class ${controllerClass} extends BackManageController<${shortModelExtClass}, ${shortSearchClass},${keyType}> {

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
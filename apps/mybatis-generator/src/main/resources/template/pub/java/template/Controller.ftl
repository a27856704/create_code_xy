package ${package};
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import ${pubInterPackage}.exception.SkException;
import ${iBaseServiceClass};
import ${modelExtClass};
import ${modelSearchClass};
import ${iServiceClass};

/**
*    @author ${author}
*    @Date ${date}
*    @description ${description}
*/
@Api(tags = "${apiTags}", description = "${apiDesc}")
@Controller(value ="${controllerName}")
@RequestMapping("${route}")
public class ${controllerClass} extends BackManageController<${modelExt}, ${modelSearch},String> {

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

	@Override
	public String getBaseView() {
		return "${baseView}";
	}


	@Override
	public String getMenuModel() {
		return "${menuModel}";
	}

}
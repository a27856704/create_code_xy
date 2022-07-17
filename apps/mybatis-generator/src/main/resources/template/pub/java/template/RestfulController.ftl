package ${package};
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RestController;
import ${pubInterPackage}.exception.SkException;
import ${iBaseServiceClass};
import ${restfulControllerClass};
import ${modelDTOClass};
import ${modelPageDTOClass};
import ${modelExtClass};
import ${modelSearchExtClass};
import ${iServiceClass};
import ${detailVOClass};
import ${listVOClass};
import ${modelVOClass};
import ${detailModelVOClass};
import ${baseController}.ApiManageController;
import org.springframework.validation.annotation.Validated;

/**
*    @author ${author}
*    @Date ${date}
*    @description ${description}
*/
@Api(tags = "${apiTags}", description = "${apiDesc}")
@RestController(value ="${controllerName}")
@RequestMapping("${route}")
@Validated
public class ${controllerClass} extends ApiManageController<${modelDTO},${modelPageDTO},${modelExt}, ${modelSearchExt},${modelVO},${detailModelVO},${detailVO},${listVO},${keyType}> {

	@Resource(name = "${serviceVar}")
	private ${iService} ${serviceVar};



	@Override
	public IBaseService<${modelExt}, ${modelSearchExt},${keyType}> getBaseService() throws SkException {
		return ${serviceVar};
	}

	@Override
	public String getBaseRoute() {
		return "${route}";
	}

	/**
	* 当前模板名称
	* @return
	*/
	@Override
	public String getModelName(){
		return "${modelName}";
	}




}
package ${package};
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ${pubInterPackage}.exception.SkException;
import ${iBaseServiceClass};
import ${modelExtClass};
import ${modelSearchExtClass};
import ${iServiceClass};
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.validation.annotation.Validated;
<#list columnList as item>
import ${enumsPackage}.${item.entityName?cap_first}${item.name?cap_first}Enum;
</#list>


/**
*    @author ${author}
*    @Date ${date}
*    @description ${description}
*/
@Api(tags = "${apiTags}", description = "${apiDesc}")
@Controller(value ="${controllerName}")
@RequestMapping("${route}")
@Validated
public class ${controllerClass} extends BackManageController<${modelExt}, ${modelSearchExt},String> {

	@Resource(name = "${serviceVar}")
	private ${iService} ${serviceVar};

	@Override
	public IBaseService<${modelExt}, ${modelSearchExt},String> getBaseService() throws SkException {
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
	@Override
	public String getMenuModelName() {
		return "${menuModelName}";
	}

	@Override
	public String getMenuPageName() {
		return "${menuPageName}";
	}

	@Override
	public String getPageName(){
		return "${pageName}";
	}


	@Override
	public void allPageExtend(Model model, HttpServletRequest request, HttpSession session) throws SkException {
		super.allPageExtend(model, request, session);
		<#list columnList as item>
		model.addAttribute("${item.name}List", ${item.entityName?cap_first}${item.name?cap_first}Enum.values());
		</#list>

	}
}
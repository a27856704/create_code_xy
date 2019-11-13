package ${package};

import ${baseSourceSearchClass};
import ${modelClass};

/**
*    @author ${author}
*    @Date ${date}
*    @description ${description}
*/

public class ${shortSearch} extends ${shortSourceBaseSearch} {

${searchField}
    public String getSourceField(){
        return ${entityName}.SOURCE;
    }
    public static ${shortSearch} getInstance() {
        return new ${shortSearch}();
    }

    @Override
    public String  setDefaultField() {
        return ${entityName}.CREATE_TIME;
    }

    @Override
    public String  toString() {
        return "${shortSearch}{}"+super.toString();
    }
${searchFieldMethod}
}
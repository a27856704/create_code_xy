package ${package};

import ${baseSearchClass};
import ${modelClass};

/**
*    @author ${author}
*    @Date ${date}
*    @description ${description}
*/

public class ${shortSearch} extends ${shortBaseSearch} {

${searchField}
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
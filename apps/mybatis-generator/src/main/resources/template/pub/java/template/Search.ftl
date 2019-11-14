package ${package};

import ${baseSearchClass};
import ${modelClass};

/**
*    @author ${author}
*    @Date ${date}
*    @description ${description}
*/

public class ${modelSearch} extends ${baseSearch} {

${searchField}
    public static ${modelSearch} getInstance() {
        return new ${modelSearch}();
    }

    @Override
    public String  setDefaultField() {
        return ${entityName}.CREATE_TIME;
    }

    @Override
    public String  toString() {
        return "${modelSearch}{}"+super.toString();
    }
${searchFieldMethod}
}
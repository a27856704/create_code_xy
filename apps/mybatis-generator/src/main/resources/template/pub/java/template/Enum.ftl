package ${package};

/**
*    @author ${author}
*    @Date ${createTime}
*    @description ${description}
*/

public enum ${enumName} {

<#list enumList as item>
    ${item.name?upper_case}("${item.type}", "${item.desc}") <#if (enumList?size==item_index+1) >; <#else>,</#if>
</#list>




    private String type;
    private String desc;

    ${enumName}(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static String getDescByType(String type) {
        if(type==null || "".equals(type)){
            type="0";
        }
        for (${enumName} var : ${enumName}.values()) {
            if (type.equalsIgnoreCase(var.getType()))
                return var.getDesc();
             }
            return ${enumName}.values()[0].getDesc();
        }

}

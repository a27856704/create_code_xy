package ${package};

/**
*    @author ${author}
*    @Date ${createTime}
*    @description ${description}
*/

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class FullConfigKeyConst {

<#list fullList as item>
    /**
    *  填充${item.desc}
    */
    public final static String ${item.name}_KEY = "${item.name}";
</#list>

    public final static String FULL_ALL_KEY = "FULL_ALL";
    public static Set<String> ALL_CONFIG = new HashSet<>();

    static {
    <#list fullList as item>
        ALL_CONFIG.add(${item.name}_KEY);
    </#list>
    }

    /**
     * 判断是否要加载对应的key 的数据
     *
     * @param fullConfigSet
     * @param key
     * @return
     */
    public static boolean hasFullData(Set<String> fullConfigSet, String key) {
        return fullConfigSet == null || fullConfigSet.contains(FULL_ALL_KEY) ||
                fullConfigSet.contains(key);
    }

    /**
     * 排除后返回剩下的
     *
     * @param unFullConfigSet
     * @return
     */
    public static Set<String> excludeFullData(Set<String> unFullConfigSet) {
        if (StringUtil.isNullOrEmpty(unFullConfigSet)) {
            return ALL_CONFIG;
        }
        return ALL_CONFIG.stream().filter(item -> unFullConfigSet.stream().noneMatch(one -> item.equalsIgnoreCase(one))).collect(Collectors.toSet());
    }

    /**
     * 从排除后的数据查询是否要加载
     * @param unFullConfigSet
     * @param key
     * @return
     */
    public static boolean hasFullDataFromUnFullData(Set<String> unFullConfigSet,String key) {
        return hasFullData(excludeFullData(unFullConfigSet),key);
    }
}

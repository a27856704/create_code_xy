<#assign fieldName=item.name+searchTypeUtils.getSuffixByType(item.searchFlag,true)>
                        <li class="col_3 row">
                            <p><#if ((item.searchFlag)==5)>非</#if>${item.descName}：</p>
                            <p>
                                <#if item.valueList?? && (item.valueList?size > 0)>
                                    <input th:type="radio" th:name="${fieldName}" value=""
                                           lay-skin="primary" javaType="integer"
                                           checked
                                    />全部
<#--
                                    <#list item.valueList as oneItem>
-->
                                        <input type="radio"
                                               javatype="${item.javaType?lower_case}"
                                               th:each="${item.name+'Item:'+r'${'+item.name+'List}'}"
                                               th:name="${fieldName}" th:value="${r'${'+item.name+'Item.type}'}" th:text="${r'${'+item.name+'Item.desc}'}"
                                               th:checked="${r'${!#lists.isEmpty(search.'+fieldName+')  &&  #lists.contains(search.'+fieldName+','+item.name+'Item.type'+'+\'\''+')}'}"
                                        />
<#--
                                    </#list>
-->
                                </#if>
                            </p>
                        </li>

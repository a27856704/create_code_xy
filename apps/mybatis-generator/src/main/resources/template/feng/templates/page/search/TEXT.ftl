<#if (item.searchFlag==3)>
    <#assign fieldNameStart=item.name+ searchTypeUtils.getSuffixByType(item.searchFlag,true)>
    <#assign fieldNameEnd=item.name+ searchTypeUtils.getSuffixByType(item.searchFlag,false)>
                        <li class="col_6 row">
                            <p>${item.descName}：</p>
                            <input type="text" class="section"
                                   name="${fieldNameStart}"
                                   th:value="${r'${search.'+(fieldNameStart)+"}"}"
                                   id="${fieldNameStart}" placeholder="请输入${item.descName}" autocomplete="off"/>
                            <span class="padding_lr_10"> ~</span>
                            <input type="text" class="section" name="${fieldNameEnd}"
                                   th:value="${r'${search.'+fieldNameEnd+"}"}"
                                   id="${fieldNameEnd}" placeholder="请输入${item.descName}" autocomplete="off"/>
                        </li>
<#else>
    <#assign fieldName=item.name+ searchTypeUtils.getSuffixByType(item.searchFlag,true)>
                        <li class="col_3 row">
                            <p>${item.descName}：</p>
                            <p>
                                <input type="text" name="${fieldName}"
                                       th:value="${r'${search.'+fieldName+"}"}"
                                       id="${fieldName}" placeholder="请输入${item.descName}" autocomplete="off"/>
                            </p>
                        </li>
</#if>

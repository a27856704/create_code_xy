<#assign fieldName=item.name+ searchTypeUtils.getSuffixByType(item.searchFlag,true)>
                        <li class="col_3 row">
                            <p>${item.descName}：</p>
                            <p>
                                <input type="text" name="${fieldName}"
                                       th:value="${r'${search.'+fieldName+"}"}"
                                       id="${fieldName}" placeholder="请输入${item.descName}" autocomplete="off"/>
                            </p>
                        </li>

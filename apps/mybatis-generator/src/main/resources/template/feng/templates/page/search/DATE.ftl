<#assign fieldNameStart=item.name+ searchTypeUtils.getSuffixByType(item.searchFlag,true)>
<#assign fieldNameEnd=item.name+ searchTypeUtils.getSuffixByType(item.searchFlag,false)>
                        <li class="col_6 row">
                            <p>${item.descName}：</p>
                            <p>
                                <input class="Wdate date"
                                       th:value="${r'${#dates.format(search.'+(fieldNameStart)+',\'yyyy-MM-dd\')}'}"
                                       name="${fieldNameStart}"
                                       id="${fieldNameStart}"
                                       type="text" placeholder="请输入" autocomplete="off"
                                       onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})"><span
                                        class="padding_lr_10"> ~</span>
                                <input class="Wdate date"
                                       th:value="${r'${#dates.format(search.'+(fieldNameEnd)+',\'yyyy-MM-dd\')}'}"
                                       name="${fieldNameEnd}"
                                       id="${fieldNameEnd}"
                                       type="text" placeholder="请输入" autocomplete="off"
                                       onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})"/>
                            </p>
                        </li>

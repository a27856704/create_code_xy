                            <div class="form_control">
                                <label for="${item.name}">${item.descName}</label>
                                <div class="form_input" th:text="${r'${#dates.format(domain.'+item.name+',\'yyyy-MM-dd HH:mm:ss\')}'}"/>
                            </div>

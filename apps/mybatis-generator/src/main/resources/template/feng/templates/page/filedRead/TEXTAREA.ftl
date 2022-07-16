                                <div class="big_form_control">
                                    <label for="${item.name}">${item.descName}</label>
                                    <div class="form_input" th:text="${r'${domain.'+item.name+'}'}"/>
                                </div>
                                
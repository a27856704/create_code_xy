                            <div class="form_control">
                                <label for="${item.name}">${item.descName}</label>
                                <div class="form_input"  th:text="${r'${domain.'+item.name+'Desc}'}"/>
                            </div>
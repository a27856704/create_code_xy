                            <div class="big_form_control">
                                <label>${item.descName}</label>
                                <div class="big_form_input">
                                    <div th:utext="${r'${domain.'+item.name+'}'}" id="${item.name}Editor" class="ckEditor" data-editorname="${item.name}"></div>
                                    <textarea name="${item.name}" id="${item.name}" style="display: none"></textarea>

                                </div>
                            </div>









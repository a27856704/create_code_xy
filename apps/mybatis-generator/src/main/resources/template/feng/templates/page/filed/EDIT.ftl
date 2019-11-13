<div class="big_form_control">
    <label>${item.descName}</label>
    <div class="big_form_input">
        <div th:text="${r'${domain.'+item.name+'}'}"></div>
        <div class="wu_editor" data-editorname="${item.name}">
        </div>
    </div>
</div>
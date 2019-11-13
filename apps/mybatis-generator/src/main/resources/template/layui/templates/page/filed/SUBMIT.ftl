<div class="layui-form-item">
    <div class="layui-input-block">
        <button class="layui-btn" lay-submit="" lay-filter="go" th:text="提交"
                sussMsg="${add?string('添加成功','修改成功')}"></button>
        <button type="reset" class="layui-btn layui-btn-primary" th:text="重置"></button>
    </div>
</div>


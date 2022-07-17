<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">

<div class="layui-side layui-bg-black" th:fragment="menu(menuName,currPage)">

    <script type="text/javascript" src="/frame/layui/layui.js"></script>
    <script type="text/javascript" src="/js/index.js"></script>

    <div class="layui-side-scroll">

        <ul class="layui-nav layui-nav-tree" lay-filter="test">

            <#list menuList as item>
                <li class="layui-nav-item"
                    th:classappend="${r'${menuName=='+"'"+item.menuName+"'}?'layui-nav-itemed'"}">
                    <a class="" href="javascript:;">${item.menuTitle}</a>
                    <#list item.subList as subItem>
                        <dl class="layui-nav-child">
                            <dd><a href="${subItem.url}"
                                   th:classappend="${r'${currPage=='+"'"+subItem.subMenu+"'&& menuName=='"+item.menuName+"'}?'layui-this'"}">${subItem.title}</a>
                            </dd>

                        </dl>
                    </#list>

                </li>
            </#list>


            -->
        </ul>

    </div>

    <script>
        //JavaScript代码区域
        layui.use('element', function () {
            var element = layui.element;

        });
    </script>
</div>



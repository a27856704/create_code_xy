<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<div class="left_menu" th:fragment="menu(menuName,currPage)">
    <div class="left_logo">
        <ul>
            <li><img src="/images/icon_logo.png"></li>
            <li>${projectName}</li>
        </ul>
    </div>
    <div class="left_menu_list" id="leftMenu">
        <dl>
            <dt class="second"><a class="icon_font no_child" href="#"><i class="icon_font home"></i>系统首页</a></dt>
        </dl>
        <#list menuList as item>
            <dl>
                <dt th:class="${r'${menuName=='+"'"+item.menuName+"'}?'second active':'second'"}">
                    <a class="icon_font" href="#">${item.menuTitle}</a>
                </dt>
                <dd th:class="${r'${menuName=='+"'"+item.menuName+"'}?'second active':'second'"}">
                    <ul>
                        <#list item.subList as subItem>
                            <li th:class="${r'${currPage=='+"'"+subItem.subMenu+"'}?'active'"}">
                                <a href="${subItem.url}"><i>●</i>${subItem.title}</a>
                            </li>
                        </#list>
                    </ul>
                </dd>
            </dl>
        </#list>
    </div>
</div>



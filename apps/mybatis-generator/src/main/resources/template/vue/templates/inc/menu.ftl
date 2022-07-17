import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

export const constantRoutes = [
{
path: '/login',
component: () => import('@/views/login/index'),
hidden: true
},

{
path: '/404',
component: () => import('@/views/404'),
hidden: true
},

{
path: '/',
component: Layout,
redirect: '/dashboard',
children: [{
path: 'dashboard',
name: 'Dashboard',
component: () => import('@/views/dashboard/index'),
meta: { title: '首页', icon: 'dashboard' }
}]
},


<#list menuList as item>
    {
    path: '/${item.menuName}',
    component: Layout,
    redirect: '/${item.menuName}',
    name:   '${item.menuName?cap_first}',
    role: '/${item.menuName}Module',
    meta: { title: '${item.menuTitle}', icon: 'el-icon-s-help' },
    children: [

    <#list item.subList as subItem>

        {
        path: '${item.menuName}List',
        name: '${item.menuName?cap_first}List',
        role: '${subItem.url}',
        component: () => import('@/views/${item.menuName}/${item.menuName?cap_first}ListPage'),
        meta: { title: '${subItem.title}', icon: 'el-icon-d-arrow-right' }
        }
    </#list>

    ]
    },
</#list>


{ path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
// mode: 'history', // require service support
scrollBehavior: () => ({ y: 0 }),
routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
const newRouter = createRouter()
router.matcher = newRouter.matcher // reset router
}

export default router

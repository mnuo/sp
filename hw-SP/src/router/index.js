import Vue from 'vue'
import Router from 'vue-router'
import _RoutePrefix from  './modules/route_prefix'

const  {
  BasePrefix, DeductPrefix, DepositPrefix, DiscountPrefix, OrderPrefix, PolicyPrefix,
  PricePrefix, ProductPrefix, ReconciliationPrefix, SaledispsalPrefix
} = _RoutePrefix;

import {
  BaseChildren, DeductChildren, DepositChildren, DiscountChildren, OrderChildren, PolicyChildren,
  PriceChildren, ProductChildren, ReconciliationChildren, SaledispsalChildren
} from './modules'
/* Layout */
import Layout from '@/views/layout'
import RoutePrefix from "./modules/route_prefix";

Vue.use(Router)

export const constantRouterMap = [
  {
    path: '/',
    name: 'Default',
    hidden: true,
    redirect: '/product'
  },
  {
    path: `${ProductPrefix}`,
    name: '产品管理',
    component: Layout,
    meta: {
      title: '产品管理',
      icon: 'fa-product-hunt'
    },
    children: ProductChildren
  },
  {
    path: `${OrderPrefix}`,
    name: '订单管理',
    component: Layout,
    meta: {
      title: '订单管理',
      //icon: 'fa-first-order'
      icon: 'fa-shopping-bag'
    },
    children: OrderChildren
  },
  {
    path: `${PricePrefix}`,
    name: '价格管理',
    component: Layout,
    meta: {
      title: '价格管理',
      //icon: 'fa-strikethrough'
      icon:'fa fa-jpy'
    },
    children: PriceChildren
  },
  {
    path: `${SaledispsalPrefix}`,
    name: '处理品销售',
    component: Layout,
    meta: {
      title: '处理品销售',
      icon: 'fa-retweet'
    },
    children: SaledispsalChildren
  },
  {
    path: `${PolicyPrefix}`,
    name: '优惠政策管理',
    component: Layout,
    meta: {
      title: '优惠政策管理',
      //icon: 'fa-sliders'
      icon:'fa-user-circle-o'
    },
    children: PolicyChildren
  },
  {
    path: `${DiscountPrefix}`,
    name: '奖励及优惠计算',
    component: Layout,
    meta: {
      title: '奖励及优惠计算',
      icon: 'fa-calculator'
    },
    children: DiscountChildren
  },
  {
    path: `${ReconciliationPrefix}`,
    name: '提货对账',
    component: Layout,
    meta: {
      title: '提货对账',
      //icon: 'fa-credit-card-alt'
      icon:'fa fa-truck'
    },
    children: ReconciliationChildren
  },
  {
    path: `${DepositPrefix}`,
    name: '定金管理',
    component: Layout,
    meta: {
      title: '定金管理',
      //icon: 'fa-money'
      //icon:'fa fa-diamond'
      icon:'fa-strikethrough'
    },
    children: DepositChildren
  },
  {
    path: `${DeductPrefix}`,
    name: '违约扣款',
    component: Layout,
    meta: {
      title: '违约扣款',
      icon: 'fa-hand-lizard-o'
    },
    children: DeductChildren
  },
  {
    path: `${BasePrefix}`,
    name: '基础资料',
    component: Layout,
    meta: {
      title: '基础资料',
      //icon: 'fa-database'
      icon:'fa fa-cubes'
    },
    children: BaseChildren
  },
]
export default new Router({
  scrollBehavior: () => ({y: 0}),
  routes: constantRouterMap
})

import RoutePrefix from './route_prefix'

const {BasePrefix} = RoutePrefix;

export default [
  {
    path: `${BasePrefix}/import/productClass`,
    name: '产品品类定义',
    component: () => import('@/views/base/import/productClass.vue'),
    meta: {
      title: '1.产品品类定义',
      noChildren: true,
    },
    hidden: true,
  },
  {
    path: `${BasePrefix}/import/productKind`,
    name: '产品类型定义',
    component: () => import('@/views/base/import/productKind.vue'),
    meta: {
      title: '2.产品类型定义',
      noChildren: true,
    },
    hidden: true,
  },
  {
    path: `${BasePrefix}/import/productBase`,
    name: '常规品、非常规品定义',
    component: () => import('@/views/base/import/productBase.vue'),
    meta: {
      title: '1.常规品、非常规品定义',
      noChildren: true,
    },
  },

  {
    path: `${BasePrefix}/import/customer`,
    name: '客户资料',
    component: () => import('@/views/base/import/customer.vue'),
    meta: {
      title: '4.客户资料',
      noChildren: true,
    },
    hidden: true,
  },
  {
    path: `${BasePrefix}/import/baseMachine`,
    name: '生产机械产能维护表',
    component: () => import('@/views/base/import/baseMachine.vue'),
    meta: {
      title: '2.生产机械产能维护表',
      noChildren: true,
    },
  },
  {
    path: `${BasePrefix}/import/customerManageDuty`,
    name: '客户分管责任人维护表',
    component: () => import('@/views/base/import/customerManageDuty.vue'),
    meta: {
      title: '3.客户分管责任人维护表',
      noChildren: true,
    },
  },
  {
    path: `${BasePrefix}/import/customerInfo`,
    name: '客户信息表',
    component: () => import('@/views/base/import/customerInfo.vue'),
    meta: {
      title: '4.客户信息表',
      noChildren: true,
    },
  },
  {
    path: `${BasePrefix}/productConvention`,
    name: '生活用纸原纸常规品列表',
    component: () => import('@/views/base/import/productConvention'),
    meta: {
      title: '5.生活用纸原纸常规品列表',
      noChildren: true,
    },
  },
  {
    path: `${BasePrefix}/productUnconvention`,
    name: '生活用纸原纸非常规品列表',
    component: () => import('@/views/base/import/productUnconvention'),
    meta: {
      title: '6.生活用纸原纸非常规品列表',
      noChildren: true,
    },
  },
  {
    path: `${BasePrefix}/productWhole`,
    name: '生活成品纸产品列表',
    component: () => import('@/views/base/import/productWhole'),
    meta: {
      title: '7.生活成品纸产品列表',
      noChildren: true,
    },
  },
  {
    path: `${BasePrefix}/classK3Code`,
    name: '不同机型对应K3物料代码',
    component: () => import('@/views/base/import/classK3Code.vue'),
    meta: {
      title: '8.不同机型对应K3物料代码维护表',
      noChildren: true,
    }
  }
]

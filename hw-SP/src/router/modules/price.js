import RoutePrefix from './route_prefix'

const {PricePrefix} = RoutePrefix;

export default [
  {
    path: `${PricePrefix}/import/Default`,
    name: '价格政策说明',
    component: () => import('@/views/price/import/Default.vue'),
    meta: {
      title: '1.价格政策说明',
      noChildren:true
    }
  },
  {
    path: `${PricePrefix}/import/price`,
    name: '产品价格',
    component: () => import('@/views/price/layout.vue'),
    meta: {
      title: '2.产品价格'
    },
    children: [
      {
        path: `${PricePrefix}/import/imPrice/list`,
        name: '常规、非常规产品统一销售价格表',
        component: () => import('@/views/price/import/imPrice_list.vue'),
        meta: {
          title: '2.1 常规、非常规产品统一销售价格表'
        }
      },
      {
        path: `${PricePrefix}/import/imPrice/add`,
        name: '产品价格录入',
        component: () => import('@/views/price/import/imPrice_add.vue'),
        meta: {
          title: '2.11 产品价格录入',
        },
        hidden:true,
      },
      // {
      //   path: `${PricePrefix}/import/priceAdjust`,
      //   name: '产品价格调整录入',
      //   component: () => import('@/views/price/import/priceAdjust.vue'),
      //   meta: {
      //     title: '2.12 产品价格调整录入'
      //   },
      //   hidden:true
      // },
      {
        path: `${PricePrefix}/import/imPriceAdjust/add`,
        name: '产品价格调整录入',
        component: () => import('@/views/price/import/imPriceAdjust_add.vue'),
        meta: {
          title: '2.12 产品价格调整录入'
        },
        hidden:true
      },
      {
        path: `${PricePrefix}/import/imPriceAdjust/list`,
        name: '产品价格调整',
        component: () => import('@/views/price/import/imPriceAdjust_list.vue'),
        meta: {
          title: '2.12 产品价格调整'
        },
        hidden:true
      },
      {
        path: `${PricePrefix}/import/priceListOld`,
        name: '历史价格查询表',
        component: () => import('@/views/price/import/priceListOld.vue'),
        meta: {
          title: '2.13 历史价格查询表'
        },
        hidden:true
      },
      {
        path: `${PricePrefix}/import/priceListOldDetail`,
        name: '历史价格明细表',
        component: () => import('@/views/price/import/priceListOldDetail.vue'),
        meta: {
          title: '2.14  历史价格明细表'
        },
        hidden:true
      },

      {
        path: `${PricePrefix}/import/espPriceList`,
        name: '特规产品销售价格表',
        component: () => import('@/views/price/import/espPriceList.vue'),
        meta: {
          title: '2.2 特规产品销售价格表'
        }
      },
      {
        path: `${PricePrefix}/import/espPriceAdjust`,
        name: '加价或优惠调整界面',
        component: () => import('@/views/price/import/espPriceAdjust.vue'),
        meta: {
          title: '2.21 加价或优惠调整界面'
        },
        hidden:true
      },
      {
        path: `${PricePrefix}/import/espPriceListOld/:ID`,
        name: '特规产品历史价格查询表',
        component: () => import('@/views/price/import/espPriceListOld.vue'),
        meta: {
          title: '2.22 特规产品历史价格查询表'
        },
        hidden:true
      }
    ]
  },
  // {
  //     // path: `${PricePrefix}/import`,
  //     // name: '特规品套产品销售价格明细',
  //     // component: () => import('@/views/price/layout.vue'),
  //     // meta: {
  //     //   title: '3 特规品套产品销售价格明细'
  //     // },
  //   path: `${PricePrefix}/import`,
  //   name: '特规品套产品销售价格明细',
  //   component: () => import('@/views/price/layout.vue'),
  //   meta: {
  //     title: '3.特规品套产品销售价格明细'
  //   },
  //     children:[
  //       {
  //         path:`${PricePrefix}/import/specialSetPrice`,
  //         name:'特规品套产品销售价格明细',
  //         component:()=>import('@/views/price/import/specialSetPrice.vue'),
  //         meta:{
  //             title:'3.特规品套产品销售价格明细',
  //             noChildren:true,
  //         }
  //      },
       
  //     ]
  // },
  {
    path: `${PricePrefix}/import/`,
    name: '特规品套产品销售价格明细',
    component: () => import('@/views/price/layout.vue'),
    meta: {
      title: '3.特规品套产品销售价格明细'
    },
    children: [
      {
        path: `${PricePrefix}/import/specialSetPrice`,
        name: '特规品套产品销售价格明细',
        component: () => import('@/views/price/import/specialSetPrice.vue'),
        meta: {
          title: '3.1 特规品套产品销售价格明细'
        }
      },
      {
        path:`${PricePrefix}/import/specialSetPriceHistory`,
        name:'特规品历史价格明细表',
        component:()=>import('@/views/price/import/specialSetPriceHistory.vue'),
        meta:{
          title:'特规品历史价格明细表',
          noChildren:true,
        },
        hidden:true
      },
    ]
  },

 
 
  // {
  //   path: `${PricePrefix}/import`,
  //   name: '特规品套产品销售价格明细',
  //   component: () => import('@/views/price/layout.vue'),
  //   meta: {
  //     title: '3 特规品套产品销售价格明细'
  //   },
  //   children:[
  //     {
  //       path: `${PricePrefix}/import/espPriceListDetail`,
  //       name: '特规品套产品销售价格明细',
  //       component: () => import('@/views/price/import/espPriceListDetail.vue'),
  //       meta: {
  //         title: ' 特规品套产品销售价格明细'
  //       }
  //     },
  //   ]
  // },
  
  {
    path: `${PricePrefix}/import/addPriceMinWidthA`,
    name: '小幅宽产品加价表',
    component: () => import('@/views/price/import/addPriceMinWidthA.vue'),
    meta: {
      title: '4.小幅宽产品加价表',
      noChildren:true
    }
},
  {
    path: `${PricePrefix}/import/addPriceMinWidthAdjust`,
    name: '小幅宽产品加价调整',
    component: () => import('@/views/price/import/addPriceMinWidthAdjust.vue'),
    meta: {
      title: '小幅宽产品加价调整',
      noChildren:true
    },
    hidden:true,
  },
  
  {
    path:`${PricePrefix}/import/widthAndBill`,
    name:'幅宽及承兑汇票结算差价表',
    component:()=>import('@/views/price/import/widthAndBill.vue'),
    meta:{
        title:'5.幅宽及承兑汇票结算差价表',
        noChildren:true,
    }
  },
  {
    path:`${PricePrefix}/import/widthAndBillAdjust`,
    name:'幅宽及承兑汇票结算差价调整',
    component:()=>import('@/views/price/import/widthAndBillAdjust.vue'),
    meta:{
        title:'5.幅宽及承兑汇票结算差价调整',
        noChildren:true,
    },
    hidden:true,
  },
  {
    path:`${PricePrefix}/import/widthAndBillHistory`,
    name:'幅宽及承兑汇票结算历史差价表',
    component:()=>import('@/views/price/import/widthAndBillHistory.vue'),
    meta:{
        title:'5.幅宽及承兑汇票结算历史差价表',
        noChildren:true,
    },
    hidden:true
  },
  {
    path:`${PricePrefix}/import/imPriceDifference/list`,
    name:'销售价格差价表',
    component:()=>import('@/views/price/import/imPriceDifference_list.vue'),
    meta:{
        title:'6.销售价格差价表',
        noChildren:true,
    },
  },
]

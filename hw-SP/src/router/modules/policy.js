import RoutePrefix from './route_prefix'

const {PolicyPrefix} = RoutePrefix;

export default [
  {
    path: `${PolicyPrefix}/import/policyCurrent`,
    name: '当月销售政策',
    component: () => import('@/views/policy/layout'),
    meta: {
      title: '一、当月销售政策'
    },
    children: [
      {
        path: `${PolicyPrefix}/import/poPrice/view`,
        name: '生活原纸产品出厂价格执行表',
        component: () => import('@/views/policy/layout'),
        meta: {
          title: '(一) 生活原纸产品出厂价格执行表'
        }
      },
      {
        path: `${PolicyPrefix}/import/policyAndOver/view`,
        name: '级别优惠及超量奖奖励标准执行',
        component: () => import('@/views/policy/layout'),
        meta: {
          title: '(二) 级别优惠及超量奖奖励标准执行'
        },
        children: [
          {
            path: `${PolicyPrefix}/import/poPolicy/view`,
            name: '当月生活原纸[优惠政策]维护奖励标准执行表',
            component: () => import('@/views/policy/import/poPolicy_view'),
            meta: {
              title: '1.当月生活原纸[优惠政策]维护奖励标准执行表'
              , executeStatus: 1
            },
          },
          {
            path: `${PolicyPrefix}/import/poOver/view`,
            name: '当月生活原纸[超量奖]维护奖励标准执行表',
            component: () => import('@/views/policy/import/poOver_list'),
            meta: {
              title: '2.当月生活原纸[超量奖]维护奖励标准执行表',
              executeStatus: 1
            }
          }
        ],
      },
      {
        path: `${PolicyPrefix}/import/customerPolicy/view`,
        name: '客户优惠政策维护',
        component: () => import('@/views/policy/layout'),
        meta: {
          title: '(三) 客户优惠政策维护'
        },
        children: [
          {
            path: `${PolicyPrefix}/import/poSupply/view`,
            name: '当月生活原纸机台产能及供货计划执行表',
            component: () => import('@/views/policy/import/poSupply_list'),
            meta: {
              title: '1.当月生活原纸机台产能及供货计划执行表'
              , executeStatus: 1
            },
          },
          {
            path: `${PolicyPrefix}/import/poPolicyMonth/view`,
            name: '当月客户【级别优惠】奖励执行表',
            component: () => import('@/views/policy/import/poPolicyMonth_list'),
            meta: {
              title: '2.当月客户【级别优惠】奖励执行表'
              , executeStatus: 1
            },
          },
          {
            path: `${PolicyPrefix}/import/poOverReward/view/3`,
            name: '当月【3900纸机白色】客户基础量及超量执行表',
            component: () => import('@/views/policy/import/poOverReward_list'),
            meta: {
              title: '3.当月【3900纸机白色】客户基础量及超量执行表',
              platform: '3900纸机',
              paperType: '白色'
              , executeStatus: 1
            },
          },
          {
            path: `${PolicyPrefix}/import/poOverReward/view/4`,
            name: '当月【3900纸机本色】客户基础量及超量执行表',
            component: () => import('@/views/policy/import/poOverReward_list'),
            meta: {
              title: '4.当月【3900纸机本色】客户基础量及超量执行表',
              platform: '3900纸机',
              paperType: '本色'
              , executeStatus: 1
            },
          },
          {
            path: `${PolicyPrefix}/import/poOverReward/view/5`,
            name: '当月【1575纸机白色】客户基础量及超量执行表',
            component: () => import('@/views/policy/import/poOverReward_list'),
            meta: {
              title: '5.当月【1575纸机白色】客户基础量及超量执行表',
              platform: '1575纸机',
              paperType: '白色'
              , executeStatus: 1
            },
          },
          {
            path: `${PolicyPrefix}/import/poOverReward/view/6`,
            name: '当月【高速纸机白色】客户基础量及超量执行表',
            component: () => import('@/views/policy/import/poOverReward_list'),
            meta: {
              title: '6.当月【高速纸机白色】客户基础量及超量执行表',
              platform: '高速纸机',
              paperType: '白色'
              , executeStatus: 1
            },
          },
          {
            path: `${PolicyPrefix}/import/poOverReward/view/7`,
            name: '当月【高速纸机本色】客户基础量及超量执行表',
            component: () => import('@/views/policy/import/poOverReward_list'),
            meta: {
              title: '7.当月【高速纸机本色】客户基础量及超量执行表',
              platform: '高速纸机',
              paperType: '本色'
              , executeStatus: 1
            },
          },
        ],
      },
    ]
  },

  {
    path: `${PolicyPrefix}/import/policyMaintain`,
    name: '销售政策维护',
    component: () => import('@/views/policy/layout'),
    meta: {
      title: '二、销售政策维护'
    },
    children: [
      {
        path: `${PolicyPrefix}/import/poPrice`,
        name: '生活原纸产品出厂价格维护',
        component: () => import('@/views/policy/layout'),
        meta: {
          title: '(一) 生活原纸产品出厂价格维护'
        }
      },
      {
        path: `${PolicyPrefix}/import/policyAndOver`,
        name: '级别优惠及超量奖奖励标准维护',
        component: () => import('@/views/policy/layout'),
        meta: {
          title: '(二) 级别优惠及超量奖奖励标准维护'
        },
        children: [
          {
            path: `${PolicyPrefix}/import/poPolicy`,
            name: '当月生活原纸[优惠政策]维护奖励标准维护表',
            component: () => import('@/views/policy/import/poPolicy_list'),
            meta: {
              title: '1.当月生活原纸[优惠政策]维护奖励标准维护表'
              , executeStatus: 0
            },
          },
          {
            path: `${PolicyPrefix}/import/poOver`,
            name: '当月生活原纸[超量奖]维护奖励标准维护表',
            component: () => import('@/views/policy/import/poOver_list'),
            meta: {
              title: '2.当月生活原纸[超量奖]维护奖励标准维护表'
              , executeStatus: 0
            }
          }
        ],
      },
      {
        path: `${PolicyPrefix}/import/customerPolicy`,
        name: '客户优惠政策维护',
        component: () => import('@/views/policy/layout'),
        meta: {
          title: '(三) 客户优惠政策维护'
        },
        children: [
          {
            path: `${PolicyPrefix}/import/poSupply`,
            name: '当月生活原纸机台产能及供货计划维护表',
            component: () => import('@/views/policy/import/poSupply_list'),
            meta: {
              title: '1.当月生活原纸机台产能及供货计划维护表'
              , executeStatus: 0
            },
          },
          {
            path: `${PolicyPrefix}/import/poPolicyMonth`,
            name: '当月客户【级别优惠】奖励维护表',
            component: () => import('@/views/policy/import/poPolicyMonth_list'),
            meta: {
              title: '2.当月客户【级别优惠】奖励维护表'
              , executeStatus: 0
            },
          },
          {
            path: `${PolicyPrefix}/import/poOverReward/3`,
            name: '当月【3900纸机白色】客户基础量及超量维护',
            component: () => import('@/views/policy/import/poOverReward_list'),
            meta: {
              title: '3.当月【3900纸机白色】客户基础量及超量维护',
              platform: '3900纸机',
              paperType: '白色'
              , executeStatus: 0
            },
          },
          {
            path: `${PolicyPrefix}/import/poOverReward/4`,
            name: '当月【3900纸机本色】客户基础量及超量维护',
            component: () => import('@/views/policy/import/poOverReward_list'),
            meta: {
              title: '4.当月【3900纸机本色】客户基础量及超量维护',
              platform: '3900纸机',
              paperType: '本色'
              , executeStatus: 0
            },
          },
          {
            path: `${PolicyPrefix}/import/poOverReward/5`,
            name: '当月【1575纸机白色】客户基础量及超量维护',
            component: () => import('@/views/policy/import/poOverReward_list'),
            meta: {
              title: '5.当月【1575纸机白色】客户基础量及超量维护',
              platform: '1575纸机',
              paperType: '白色'
              , executeStatus: 0
            },
          },
          {
            path: `${PolicyPrefix}/import/poOverReward/6`,
            name: '当月【高速纸机白色】客户基础量及超量维护',
            component: () => import('@/views/policy/import/poOverReward_list'),
            meta: {
              title: '6.当月【高速纸机白色】客户基础量及超量维护',
              platform: '高速纸机',
              paperType: '白色'
              , executeStatus: 0
            },
          },
          {
            path: `${PolicyPrefix}/import/poOverReward/7`,
            name: '当月【高速纸机本色】客户基础量及超量维护',
            component: () => import('@/views/policy/import/poOverReward_list'),
            meta: {
              title: '7.当月【高速纸机本色】客户基础量及超量维护',
              platform: '高速纸机',
              paperType: '本色'
              , executeStatus: 0
            },
          },
        ],
      },
    ]
  },
  {
    path: `${PolicyPrefix}/import/subsidyMaintain`,
    name: '当月补贴政策',
    component: () => import('@/views/policy/layout'),
    meta: {
      title: '三、当月补贴政策'
    },
    children: [
      {
        path: `${PolicyPrefix}/import/policyTransportPrice/maintain`,
        name: '（一）当月生活原纸【运输补助】维护表',
        component: () => import('@/views/policy/import/poTransport_list.vue'),
        meta: {
          title: '（一）当月生活原纸【运输补助】维护表',
          noChildren: true,
        }
      },
      {
        path: `${PolicyPrefix}/import/policyTransportPrice/check`,
        name: '当月生活原纸【运输补贴】审核表',
        component: () => import('@/views/policy/import/poTransport_check.vue'),
        meta: {
          title: '当月生活原纸【运输补贴】审核表',
          noChildren: true,
        },
        hidden: true,
      },
      {
        path: `${PolicyPrefix}/import/policyAreaPrice/maintain`,
        name: '（二）当月生活原纸【区域价格补助】维护表',
        component: () => import('@/views/policy/import/poArea_list.vue'),
        meta: {
          title: '（二）当月生活原纸【区域价格补助】维护表',
          noChildren: true,
        }
      },
      {
        path: `${PolicyPrefix}/import/policyAreaPrice/check`,
        name: '当月生活原纸【区域价格补助】审核表',
        component: () => import('@/views/policy/import/poArea_check.vue'),
        meta: {
          title: '当月生活原纸【区域价格补助】审核表',
          noChildren: true,
        },
        hidden: true,
      }
      ]},
  {
    path: `${PolicyPrefix}/import/discount`,
    name: '折扣管理',
    component: () => import('@/views/policy/layout'),
    meta: {
      title: '三、折扣管理'
    },
    children: [
      {
        path: `${PolicyPrefix}/import/poDiscount`,
        name: '折扣信息单维护',
        component: () => import('@/views/policy/import/poDiscount_list'),
        meta: {
          title: '(一) 折扣信息单维护'
        }
      },
      {
        path: `${PolicyPrefix}/import/poDiscountOrder`,
        name: '折扣计算单维护',
        component: () => import('@/views/policy/import/poDiscountOrder_list'),
        meta: {
          title: '(二) 折扣计算单维护'
        }
      },
    ]
  }
]

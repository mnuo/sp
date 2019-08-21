import RoutePrefix from './route_prefix'

const { OrderPrefix } = RoutePrefix;

export default [
    {
        path: `${OrderPrefix}/heightSpeed`,
        name: '进口高速纸机',
        component: () => import('@/views/order/index'),
        meta: {
            title: '进口高速纸机'
        },
        children: [
            {
                path: `${OrderPrefix}/heightSpeed/clientOrder`,
                component: () => import('@/views/order/heightSpeed/clientOrder'),
                name: '客户订单明细',
                meta: {
                    title: '客户订单明细'
                }
            },
            {
                path: `${OrderPrefix}/heightSpeed/clientDetail`,
                name: '客户订单明细详情',
                component: () => import('@/views/order/heightSpeed/clientDetail'),
                meta: {
                    noChildren:true,
                    title: '客户订单明细详情'
                },
                hidden: true
            },
            {
                path: `${OrderPrefix}/heightSpeed/toBePaidOredr`,
                component: () => import('@/views/order/heightSpeed/toBePaidOredr'),
                name: '待缴定金订单',
                meta: {
                    title: '待缴定金订单'
                },
            },
            {
                path: `${OrderPrefix}/heightSpeed/toBeProductOrder`,
                component: () => import('@/views/order/heightSpeed/toBeProductOrder'),
                name: '待生产订单',
                meta: {
                    title: '待生产订单'
                },
            },
            {
                path: `${OrderPrefix}/heightSpeed/unTakeGoodsOrder`,
                component: () => import('@/views/order/heightSpeed/unTakeGoodsOrder'),
                name: '已生成未提货完成订单',
                meta: {
                    title: '已生成未提货完成订单'
                },
            },
            {
                path: `${OrderPrefix}/heightSpeed/unTakeGoodsOrderDetail`,
                name: '已生成未提货完成订单明细',
                component: () => import('@/views/order/heightSpeed/unTakeGoodsOrderDetail'),
                meta: {
                    noChildren:true,
                    title: '已生成未提货完成订单明细'
                },
                hidden: true
            },
            {
                path: `${OrderPrefix}/heightSpeed/fineshOrder`,
                component: () => import('@/views/order/heightSpeed/fineshOrder'),
                name: '已完成订单',
                meta: {
                    title: '已完成订单'
                },
            },
            {
                path: `${OrderPrefix}/heightSpeed/fineshOrderDetail`,
                name: '已提货完成订单明细',
                component: () => import('@/views/order/heightSpeed/fineshOrderDetail'),
                meta: {
                    noChildren:true,
                    title: '已提货完成订单明细'
                },
                hidden: true
            },
            {
                path: `${OrderPrefix}/heightSpeed/changeOrder`,
                component: () => import('@/views/order/heightSpeed/changeOrder'),
                name: '订单产品调拨',
                meta: {
                    title: '订单产品调拨'
                },
            },
            {
                path: `${OrderPrefix}/heightSpeed/monthAllotOrder`,
                component: () => import('@/views/order/heightSpeed/monthAllotOrder'),
                name: '月度销售分配计划',
                meta: {
                    title: '月度销售分配计划'
                },
            },
        ]
    },
    {
        path: `${OrderPrefix}/domestic3900`,
        name: '国产3900纸机',
        component: () => import('@/views/order/index'),
        meta: {
            title: '国产3900纸机',
            noChildren:true,
        },
    },
    {
        path: `${OrderPrefix}/domestic1575`,
        name: '国产1575纸机',
        component: () => import('@/views/order/index'),
        meta: {
            title: '国产1575纸机',
            noChildren:true,
        },
    },
    {
        path: `${OrderPrefix}/marketingAllot`,
        name: '销售分配表',
        component: () => import('@/views/order/index'),
        meta: {
            title: '销售分配表',
            noChildren:true,
        },
    },
]
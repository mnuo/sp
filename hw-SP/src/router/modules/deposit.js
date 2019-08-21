import RoutePrefix from './route_prefix'

const { DepositPrefix } = RoutePrefix;

export default [
    {
        path: `${DepositPrefix}/import/dDepositStandard`,
        name: '产品定金标准',
        component: () => import('@/views/deposit/import/dDepositStandard.vue'),
        meta: {
            title: '1.产品定金标准',
            noChildren:true,
        },
    },
    {
        path: `${DepositPrefix}/import/dDepositStandardAdjust`,
        name: '产品定金标准调整',
        component: () => import('@/views/deposit/import/dDepositStandardAdjust.vue'),
        meta: {
            title: '产品定金标准调整',
            noChildren:true,
        },
        hidden:true
    },
    {
        path: `${DepositPrefix}/import/dDepositStandardHistory`,
        name: '产品定金标准历史记录',
        component: () => import('@/views/deposit/import/dDepositStandardHistory.vue'),
        meta: {
            title: '产品定金标准历史记录',
            noChildren:true,
        },
        hidden:true
    },
    {
        path: `${DepositPrefix}/import/dNoDepositCustomer`,
        name: '当月不收取定金的非常规品明细表',
        component: () => import('@/views/deposit/import/dNoDepositCustomer.vue'),
        meta: {
            title: '2.当月不收取定金的非常规品明细表',
            noChildren:true,
        }
    },
    {
        path:`${DepositPrefix}/import/dNoDepositStandard`,
        name:'不收取定金标准',
        component:()=>import('@/views/deposit/import/dNoDepositStandard.vue'),
        meta:{
            title:'不收取定金标准',
            noChildren:true,
        },
        hidden:true,
    },
    {
        path:`${DepositPrefix}/import/dNoDepositStandardHistory`,
        name:'历史不收取定金标准',
        component:()=>import('@/views/deposit/import/dNoDepositStandardHistory.vue'),
        meta:{
            title:'历史不收取定金标准',
            noChildren:true,
        },
        hidden:true,
    },
    {
        path:`${DepositPrefix}/import/dDepositMain`,
        name:'客户定金对账主表',
        component:()=>import('@/views/deposit/import/dDepositMain.vue'),
        meta:{
            title:'3.客户定金对账主表',
            noChildren:true,
        },
        // hidden:true,
    },
]
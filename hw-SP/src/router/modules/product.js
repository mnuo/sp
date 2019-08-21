import RoutePrefix from './route_prefix'

const { ProductPrefix } = RoutePrefix;

export default [
    {
        path: `${ProductPrefix}/illustrate`,
        name: '编号规则',
        component: () => import('@/views/product/illustrate'),
        meta: {
            noChildren:true,
            title: '编号规则'
        },
    },
    {
        path: `${ProductPrefix}/heightSpeed`,
        name: '进口高速纸机',
        component: () => import('@/views/product/layout'),
        meta: {
            title: '进口高速纸机',
        },
        children: [
            {
                path: `${ProductPrefix}/heightSpeed/ruleRoll`,
                component: () => import('@/views/product/roll'),
                name: '常规卫卷',
                meta: {
                    title: '常规卫卷'
                },
                children: [
                    {
                      path: `${ProductPrefix}/heightSpeed/ruleRoll/convention_size/1`,
                      component: () => import('@/views/product/roll/convention_size'),
                      name: '产品规格',
                      meta: { 
                          title: '产品规格',
                          type: 'a',
                          machine: 'Import',
                          paper: 'A',
                        }
                    },
                    {
                        path: `${ProductPrefix}/heightSpeed/ruleRoll/convention_number`,
                        component: () => import('@/views/product/roll/convention_number'),
                        name: '使用数量统计',
                        meta: { 
                            title: '使用数量统计',
                            type: 'a',
                            machine: 'Import',
                            paper: 'A',
                        }
                    },
                    {
                        path: `${ProductPrefix}/heightSpeed/ruleRoll/convention_client`,
                        component: () => import('@/views/product/roll/convention_client'),
                        name: '客户使用品种及数量统计',
                        meta: { 
                            title: '客户使用品种及数量统计',
                            type: 'a',
                            machine: 'Import',
                            paper: 'A',
                        }
                    },
                ]
            },
            {
                path: `${ProductPrefix}/heightSpeed/unruleRoll`,
                component: () => import('@/views/product/roll'),
                name: '非常规卫卷',
                meta: {
                    title: '非常规卫卷'
                },
                children: [
                    {
                        path: `${ProductPrefix}/heightSpeed/unruleRoll/convention_size/2`,
                        component: () => import('@/views/product/roll/convention_size'),
                        name: '产品规格',
                        meta: { 
                            title: '产品规格',
                            type: 'b',
                            machine: 'Import',
                            paper: 'A',
                        }
                    },
                    {
                        path: `${ProductPrefix}/heightSpeed/unruleRoll/convention_number2`,
                        component: () => import('@/views/product/roll/convention_number2'),
                        name: '使用数量统计',
                        meta: { 
                            title: '使用数量统计',
                            type: 'b',
                            machine: 'Import',
                            paper: 'A',
                        }
                    },
                    {
                        path: `${ProductPrefix}/heightSpeed/unruleRoll/convention_client`,
                        component: () => import('@/views/product/roll/convention_client'),
                        name: '客户使用品种及数量统计',
                        meta: { 
                            title: '客户使用品种及数量统计',
                            type: 'b',
                            machine: 'Import',
                            paper: 'A',
                        }
                    }
                ]
            },
            {
                path: `${ProductPrefix}/heightSpeed/specialRoll`,
                component: () => import('@/views/product/roll'),
                name: '特规卫卷',
                meta: {
                    title: '特规卫卷'
                },
                children: [
                    {
                        path: `${ProductPrefix}/heightSpeed/specialRoll/convention_size/2`,
                        component: () => import('@/views/product/roll/convention_size'),
                        name: '产品规格',
                        meta: { 
                            title: '产品规格',
                            type: 'c',
                            machine: 'Import',
                            paper: 'A',
                        }
                    },
                    {
                        path: `${ProductPrefix}/heightSpeed/specialRoll/convention_number2`,
                        component: () => import('@/views/product/roll/convention_number2'),
                        name: '使用数量统计',
                        meta: { 
                            title: '使用数量统计',
                            type: 'c',
                            machine: 'Import',
                            paper: 'A',
                        }
                    },
                    {
                        path: `${ProductPrefix}/heightSpeed/specialRoll/convention_client`,
                        component: () => import('@/views/product/roll/convention_client'),
                        name: '客户使用品种及数量统计',
                        meta: { 
                            title: '客户使用品种及数量统计',
                            type: 'c',
                            machine: 'Import',
                        }
                    }
                ]
            },
            {
                path: `${ProductPrefix}/heightSpeed/tissue`,
                component: () => import('@/views/product/tissue'),
                name: '纸巾纸(软抽)',
                meta: {
                    title: '纸巾纸(软抽)'
                },
            },
            {
                path: `${ProductPrefix}/heightSpeed/napkin`,
                component: () => import('@/views/product/napkin'),
                name: '餐巾纸',
                meta: {
                    title: '餐巾纸'
                },
            },
            {
                path: `${ProductPrefix}/heightSpeed/rollDistributor`,
                component: () => import('@/views/product/layout'),
                name: '卫卷产品单幅品种',
                meta: {
                    title: '卫卷产品单幅品种'
                },
                children:[
                    {
                        path: `${ProductPrefix}/heightSpeed/rollDistributor/useNum`,
                        component: () => import('@/views/product/rollDistributor/useNum'),
                        name: '使用数量统计',
                        meta: { title: '使用数量统计',}
                    }
                ]
            },
        ]
    },
    {
        path: `${ProductPrefix}/domestic3900`,
        name: '国产3900纸机',
        component: () => import('@/views/product/layout'),
        meta: {
            title: '国产3900纸机',
            noChildren:true,
        },
    },
    {
        path: `${ProductPrefix}/domestic1575`,
        name: '国产1575纸机',
        component: () => import('@/views/product/layout'),
        meta: {
            title: '国产1575纸机',
            noChildren:true,
        },
    },
]

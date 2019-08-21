import RoutePrefix from './route_prefix'

const { DiscountPrefix } = RoutePrefix;

export default [
    {
        path: `${DiscountPrefix}/hello10`,
        name: '奖励及优惠计算1',
        component: () => import('@/components/HelloWorld.vue'),
        meta: {
            title: '奖励及优惠计算1'
        },
    }
]
import RoutePrefix from './route_prefix'

const { SaledispsalPrefix } = RoutePrefix;

export default [
    {
        path: `${SaledispsalPrefix}/hello10`,
        name: '处理品销售1',
        component: () => import('@/components/HelloWorld.vue'),
        meta: {
            title: '处理品销售1'
        },
    }
]
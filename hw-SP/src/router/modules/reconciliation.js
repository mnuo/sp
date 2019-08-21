import RoutePrefix from './route_prefix'

const { ReconciliationPrefix } = RoutePrefix;

export default [
    {
        path: `${ReconciliationPrefix}/hello10`,
        name: '提货对账1',
        component: () => import('@/components/HelloWorld.vue'),
        meta: {
            title: '提货对账1'
        },
    }
]
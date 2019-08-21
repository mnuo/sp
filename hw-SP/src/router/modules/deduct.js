import RoutePrefix from './route_prefix'

const { DeductPrefix } = RoutePrefix;

export default [
    {
        path: `${DeductPrefix}/hello10`,
        name: '违约扣款1',
        component: () => import('@/components/HelloWorld.vue'),
        meta: {
            title: '违约扣款1'
        },
    }
]
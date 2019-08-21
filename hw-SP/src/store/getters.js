import {getCookie, UserKey} from '@/utils/Cookie'
import {constantRouterMap} from '@/router'

const getters = {
  sidebar: state => state.app.sidebar,
  login_user: () => JSON.parse(getCookie(UserKey) || '{}'),
  // permission_routers: state => state.permission.routers,
  permission_routers: () => constantRouterMap,

}
export default getters

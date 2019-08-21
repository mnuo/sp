import Cookies from 'js-cookie'

export const TokenKey = process.env.NODE_ENV === 'production' ? 'hwagain_sso_token' : 'hwagain_sso_token_test' //登录token
export const UserKey = 'user' //登录用户
export const FromUrlKey = 'from_url' //来源url，用于菜单返回时展开
export const AppsKey = 'apps' //权限系统列表

export const CookieKey = {
  TokenKey,
  UserKey,
  FromUrlKey,
  AppsKey
}

export function getCookie(CookieKey) {
  return Cookies.get(CookieKey) || ''
}

export function setCookie(CookieKey, CookieValue) {
  return Cookies.set(CookieKey, CookieValue, {
    domain: '.hwagain.com',
    path: '/'
  })
}

export function removeCookie(CookieKey) {
  return Cookies.remove(CookieKey, { domain: '.hwagain.com', path: '/' })
}

import router from '@/router'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css'// progress bar style
import { getCookie,TokenKey } from '@/utils/Cookie'

NProgress.configure({ showSpinner: false })// NProgress Configuration

router.beforeEach((to, from, next) => {
    NProgress.start()
    if (!getCookie(TokenKey)) { 
        window.location.href=process.env.LOGIN_PATH+window.location.href;
    }
    next();
    NProgress.done()
});

router.afterEach((to,from)=>{
    NProgress.done() // finish progress bar
})
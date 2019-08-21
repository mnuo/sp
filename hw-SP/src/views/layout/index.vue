<template>
  <div :class="classObj" class="app-wrapper">
    <el-container>
      <el-header height="36px">
        <el-row>
          <el-col :span="8">
            <h3 class="logo-text">{{$route.meta.title || '产品及销售信息管理平台'}}</h3>
          </el-col>
          <el-col :span="4">
            <ul class="nav-right-menu">
              <li>
                <i class="fa fa-user-circle-o"></i>
                <span>{{login_user.name || ''}}</span>
              </li>
              <li @click="logout()">
                <i class="fa fa-sign-out"></i>
                <span>注销</span>
              </li>
            </ul>
          </el-col>
        </el-row>
      </el-header>
      <el-container>
        <el-aside width="40px" class="sidebar-container">
          <sidebar class="sidebar-container"></sidebar>
        </el-aside>
        <el-container>
          <el-main>
            <div class="layout-container">
              <router-view></router-view>
            </div>
          </el-main>
        </el-container>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import { Navbar, AppMain } from './components'
import Sidebar from '@/views/layout/components/Sidebar'
import { mapGetters } from 'vuex'

export default {
  name: 'Layout',
  data() {
    return {
      isCollapse: false
    }
  },
  components: {
    Navbar,
    Sidebar,
    AppMain
  },
  //   mixins: [ResizeMixin],
  computed: {
    ...mapGetters(['login_user']),
    sidebar() {
      return this.$store.state.app.sidebar
    },
    classObj() {
      return {
        hideSidebar: !this.sidebar.opened,
        openSidebar: this.sidebar.opened,
        withoutAnimation: this.sidebar.withoutAnimation
        // mobile: this.device === 'mobile'
      }
    }
  },
  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath)
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath)
    },
    handleClickOutside() {
      this.$store.dispatch('closeSideBar', { withoutAnimation: false })
    },
    logout(){
      debugger;
      window.location.href= process.env.LOGIN_PATH + window.location.href;
    },
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
@import 'src/styles/mixin.scss';
@import 'src/styles/variables.scss';
.app-wrapper {
  @include clearfix;
  position: relative;
  height: 100%;
  width: 100%;
  .el-container {
    height: 100%;
  }
  .el-header {
    position: relative;
    background-color: $light-blue;
    color: #333;
    text-align: center;
    line-height: 36px;
    .logo-text {
      margin: 0;
      text-align: left;
      font-size: 15px;
      color: #f1f1f1;
    }
    .nav-right-menu {
      position: absolute;
      right: 0px;
      list-style: none;
      line-height: 36px;
      margin: 0;
      color: #fff;
      font-size: 14px;
      li {
        margin-right: 30px;
        float: left;
        cursor: pointer;
        i.fa {
          font-size: 16px;
        }
        span {
          font-size: 13px;
        }
      }
    }
  }

  .el-aside {
    // background-color: #D3DCE6;
    background-color: #e9eef3;
    color: #333;
    text-align: center;
    .el-menu {
      // background:#304156;
    }
  }

  .el-main {
    background-color: #e9eef3;
    color: #333;
    text-align: center;
    padding: 10px;
  }
  .layout-container {
    width: 100%;
    height: 100%;
    background: #fff;
    padding: 10px;
  }
}
</style>

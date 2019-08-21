<template>
  <div @mouseover="$store.dispatch('showSideBar')" @mouseout="$store.dispatch('hideSideBar') ">
    <div class="left-nav_menu">
      <el-menu :default-active="current_parent_path" 
        :collapse="false"
        @select="handleOpen"
        background-color="#304156"  >
        <el-menu-item :index="routeRoot.path" 
        v-for="(routeRoot,index) in permission_routers" 
        :key="index" v-if="!routeRoot.hidden">
          <el-tooltip :content="routeRoot.name" effect="light" placement="right-start" >
            <!-- <i class="el-icon-menu"></i> -->
            <i :class="['fa',routeRoot.meta.icon||'']" aria-hidden="true"></i>
            <!-- <span slot="title">{{routeRoot.meta.title}}</span> -->
          </el-tooltip>
        </el-menu-item>
      </el-menu>
    </div>
    <!-- style="{width: $store.state.app.sidebar.rightShow ? (parseInt($store.state.app.sidebar.width) - 40 + 'px') : 0}" -->
    <el-scrollbar :style="{width: $store.state.app.sidebar.rightShow ? '360px' : 0}" style="height:100%;display: inline-block;position: fixed;left: 40px;transition: width 0.2s;" wrap-class="scrollbar-wrapper right-nav_menu">
      <el-menu
        :show-timeout="200"
        :default-active="$route.path"
        :collapse="isCollapse"
        mode="vertical"
        background-color="#304156"
        text-color="#bfcbd9"
        active-text-color="#409EFF"
        collapse-transition
      >
        <sidebar-item v-for="route in current_routers" :key="route.path" :item="route" :base-path="route.path"/>
      </el-menu>
    </el-scrollbar>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import SidebarItem from './SidebarItem'

export default {
  components: { SidebarItem },
  data(){
    return {
      current_parent_path: '',
      current_routers:[],
    }
  },
  computed: {
    ...mapGetters([
      'permission_routers',
      'sidebar'
    ]),
    isCollapse() {
      return !this.sidebar.opened
    },
  },
  watch:{
    current_parent_path(val){
      let routers = this.permission_routers.filter(item => item.path == val);
      if(routers){
        if(routers[0].children.length == 1){
          let router = routers[0].children[0];
          router.children = router.children || [];
          this.current_routers = [router];
        }else {
          let router = routers[0].children;
          this.current_routers = router;
        }
      }
    }
  },
  methods:{
    handleOpen(key){
      this.current_parent_path = key;
    }
  },
  created(){
      this.current_parent_path = this.$route.matched[0].path;
  }
}
</script>

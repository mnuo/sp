<template>
    <div class="product-container">
       月度销售分配计划
    </div>
</template>

<script>
export default {
    data(){
        return{
        }
    },
    watch:{
        '$route': function () {
            this.init();
        }
    },
    methods:{
        init(){
           
        }
    },
    mounted(){
        this.init()
        
    }
}
</script>
<style scoped>
</style>
<template>
    <div class="product-container">
       订单产品调拨
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
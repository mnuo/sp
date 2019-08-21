import fetch from  '@/utils/request'
// import qs from 'qs'


const Product_API = {
    saveProduct: (params) => {
        return fetch.post(`product/save`,params)
    },
    findProduct: (params) => {//产品规格
        return fetch.get(`product/product/findByList`,{params})
    },
    findPickUpByList: (params) => {//数量统计（常规）
        return fetch.get(`product/product/findPickUpByList`,{params})
    },
    findCustomersPickUpByList: (params) => {//客户使用量统计表（常规）
        return fetch.get(`/product/product/findCustomersPickUpByList`,{params})
    },
}

export default Product_API
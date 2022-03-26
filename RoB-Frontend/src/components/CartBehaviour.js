import axios from 'axios'


var config = require('../../config')

var backendConfigurer = function () {
  switch (process.env.NODE_ENV) {
    case 'development':
      return 'http://' + config.dev.backendHost + ':' + config.dev.backendPort
    case 'production':
      return 'https://' + config.build.backendHost + ':' + config.build.backendPort
  }
}

var frontendConfigurer = function () {
  switch (process.env.NODE_ENV) {
    case 'development':
      return 'http://' + config.dev.host + ':' + config.dev.port
    case 'production':
      return 'https://' + config.build.host + ':' + config.build.port
  }
}

var backendUrl = backendConfigurer()
var frontendUrl = frontendConfigurer()


var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl}
})


export default{
    name:'cart',
    data(){
        return{
            cartOwner:sessionStorage.getItem('currentUsername'),
            cart:'',
            errorCart:'',
            response:[]
        }
    },

    created: function(){
        AXIOS.get('/applicationUsers/'.concat(cartOwner))
        .then(response => {
            this.cart = response.data.cart
        })
        .catch(e => {
            this.errorCart = e
        })
    },

    method:{
        purchaseItemsInCart: function(orderId){
            AXIOS.post('/purchases/'.concat(orderId),{},
            {params:{
                orderId:orderId,
                cartId:cart.cartId,
                username:cartOwner,
            }}).catch(e => {
                console.log(e.response.data.message)
            })
        }
    }

}
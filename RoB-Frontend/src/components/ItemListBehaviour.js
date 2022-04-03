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
    name:'itemlist',
    data(){
        return{
            products:[],
            requiredQuantity:'',
            reservationId:'',
            errorProducts:'',
            response:[],
            username: sessionStorage.getItem('currentUsername'),
            appUser: '',
            searchBar: ''
        }
    },

    created: function(){
        AXIOS.get('/items/')
        .then(response => {
            this.products = response.data
        })
        .catch(e => {
            this.errorProducts = e
        })

        AXIOS.get('/applicationUsers/'.concat(this.username))
        .then(response => {
            this.appUser = response.data
        })
        .catch(e => {
            this.errorProfile = e
        })
    },

    methods:{

        reserveItem: function(reservationId, quantity, itemName){

            AXIOS.post('/reservations/'.concat(reservationId),{},
            {params:{
                user:this.username,
                quantity:quantity,
                item:itemName
            }}).catch(e => {
                console.log(e.response.data.message)
            })
        },

        addToCart: function(itemName, itemPrice, itemSizes){
          AXIOS.put('/cart/add/'.concat(this.appUser.cart.cartId),{},
          {params:{
              itemName: itemName,
              price: itemPrice,
              size: itemSizes,
          }})
          .catch(e => {
              console.log(e.response.data.message)
          })
        },

        searchForItem: function(keyWord){
          AXIOS.get('/items/search/'.concat(keyWord))
          .then(response => {
            this.products = response.data
          })
          .catch(e => {
            this.errorProfile = e
          })
        }

    }




}
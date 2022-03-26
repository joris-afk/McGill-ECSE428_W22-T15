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
            errorProducts:'',
            response:[]
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
    },

    method:{
        reserveItem: function(reservationId, quantity, itemName){
            AXIOS.post('/reservations/'.concat(reservationId),{},
            {params:{
                username:sessionStorage.getItem('currentUsername'),
                quantity:quantity,
                itemName:itemName
            }}).catch(e => {
                console.log(e.response.data.message)
            })
        }



    }




}
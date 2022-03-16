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

function ItemDto(name,price,availableSizes){
    this.name=name
    this.price=price
    this.availableSizes=availableSizes
}

export default{
    name:'item',
    data(){
        return{
            loginUsername: sessionStorage.getItem('loginUsername'),
            appUser:'',
            userItems: [],
            newName: '',
            newPrice: '',
            newAvailableSizes: '',
            errorUser:'',
            errorItem: '',
            errorEditAccount: '',
            response:[]
        }
    },
    created: function(){
        AXIOS.get('/applicationUsers/'.concat(this.loginUsername))
        .then(response => {
            this.appUser = response.data
            this.userItems = response.data.items
        })
        .catch(e => {
            this.errorUser = e
        })
    },
    methods: {
        createItem: function(newName,newPrice,newAvailableSizes){

            AXIOS.post('/items/'.concat(newName),{},
            {params:{
                price:newPrice,
                availableSizes:newAvailableSizes
            }}).then(response => {
                this.userItems.push(response.data)
                this.newName=''
                this.newPrice=''
                this.newAvailableSizes=''
                this.errorUser=''
            }).catch(e => {
                var errorMsg = e.response.data.message
                console.log(errorMsg)
                this.errorItem = errorMsg
            })

            AXIOS.put('/applicationUsers/'.concat(loginUsername),{},
            {params:{
                items: this.userItems
            }})
            .then(response => {
                sessionStorage.setItem("loginUsername", loginUsername)
            })
            .catch(e => {
                var errorMsg = e.response.data.message
                console.log(errorMsg)
                this.errorEditAccount = errorMsg
            })

            location.reload();
        }
    }
}
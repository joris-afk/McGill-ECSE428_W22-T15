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
            editPrice: '',
            editAvailableSizesAdd: '',
            editAvailableSizesRemove: '',
            checkedItem: '',
            errorUser:'',
            errorItem: '',
            errorEditAccount: '',
            errorEditItem: '',
            errorDeleteItem: '',
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
                this.newPrice=''
                this.newAvailableSizes=''
                this.errorUser=''
            }).catch(e => {
                var errorMsg = e.response.data.message
                console.log(errorMsg)
                this.errorItem = errorMsg
            })

            //var Username = sessionStorage.getItem('currentUsername');

            AXIOS.put('/applicationUsers/'.concat(Username),{},
            {params:{
                item:newName
            }}).then(response => {
                this.newName=''
            }).catch(e => {
                var errorMsg = e.response.data.message
                console.log(errorMsg)
                this.errorItem = errorMsg
            })

            location.reload();
        },
        editPrice: function(priceToEdit){

            var itemName = this.checkedItem;

            AXIOS.patch('/items/'.concat(itemName),{},
            {params:{
                price:priceToEdit
            }}).then(response => {
                this.userItems.map(x => x.name).remove(itemName)
                this.userItems.push(response.data)
                this.editPrice=''
                this.errorEditItem=''
            }).catch(e => {
                var errorMsg = e.response.data.message
                console.log(errorMsg)
                this.errorItem = errorMsg
            });

            location.reload();
        },
        addSize: function(editAvailableSizesAdd){

            var itemName = this.checkedItem;

            AXIOS.patch('/items/'.concat(itemName),{},
            {params:{
                AddedSize:editAvailableSizesAdd
            }}).then(response => {
                this.userItems.map(x => x.name).remove(itemName)
                this.userItems.push(response.data)
                this.editAvailableSizesAdd=''
                this.errorEditItem=''
            }).catch(e => {
                var errorMsg = e.response.data.message
                console.log(errorMsg)
                this.errorItem = errorMsg
            });

            location.reload();
        },
        removeSize: function(editAvailableSizesRemove){

            var itemName = this.checkedItem;

            AXIOS.patch('/items/'.concat(itemName),{},
            {params:{
                RemovedSize: editAvailableSizesRemove
            }}).then(response => {
                this.userItems.map(x => x.name).remove(itemName)
                this.userItems.push(response.data)
                this.editAvailableSizesRemove=''
                this.errorEditItem=''
            }).catch(e => {
                var errorMsg = e.response.data.message
                console.log(errorMsg)
                this.errorItem = errorMsg
            });

            location.reload();
        },
        deleteItem: function(oldName){
            var vm=this
            AXIOS.delete('/items/'.concat(oldName),{},{}
            ).then(response => {
                this.userItems.map(x => x.name).remove(oldName)
            }).catch(e => {
                var errorMsg = e.response.data.message
                console.log(errorMsg)
                this.errorItem = errorMsg
            })

            location.reload();
        }
    }
}
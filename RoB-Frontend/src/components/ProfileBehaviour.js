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

// get data from login page or edit account info page
var loginUsername= sessionStorage.getItem('loginUsername');

export default {
    name: 'profile',
    
    data(){
      return{ 
        appUser:{
            username: '',
            fullname:'',
            address:'',
        },      
        testMessage: 'test',
        appUsers: [],
        errorProfile: '',
        response: [],

      }
    },

    created: function() {
        AXIOS.get('/applicationUsers/'.concat(loginUsername))
        .then(response => {
            this.appUser = response.data
        })
        .catch(e => {
            this.errorProfile = e
        })
        // move to the end when backend is connected
        // pass data to edit account page
        sessionStorage.setItem("currentUsername", this.appUser.username); 
    },

}






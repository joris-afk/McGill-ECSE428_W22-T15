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



export default {
    name: 'profile',
    
    data(){
      return{ 
        // get data from login page or edit account info page
        loginUsername: sessionStorage.getItem('loginUsername'),
        // appUser:{
        //     username: '',
        //     fullname:'',
        //     address:'',
        // },      
        appUser:'',
        testMessage: 'test',
        appUsers: [],
        errorProfile: '',
        response: [],

      }
    },

    created: function() {
        AXIOS.get('/applicationUsers/'.concat(this.loginUsername))
        .then(response => {
            this.appUser = response.data
            // this.appUsers.username = response.data.username
            // this.appUsers.fullname = response.data.fullname
            // this.appUsers.address = response.data.address
        })
        .catch(e => {
            this.errorProfile = e
        })
        // move to the end when backend is connected
        // pass data to edit account page
        sessionStorage.setItem("currentUsername", this.loginUsername); 
    },

}






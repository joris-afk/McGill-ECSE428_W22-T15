import axios from 'axios'

var config = require('../../config')

var frontendUrl= 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

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
        appUser:{
            username: 'user123',
            fullname:'david',
            address:'rue 100',
        },      
        testMessage: 'test',
        appUsers: [],
        errorProfile: '',
        response: [],

      }
    },

    created: function() {

        // move to the end when backend is connected
        // pass data to edit account page
        sessionStorage.setItem("Username", this.appUser.username); 

        
        // retrieve user list from backend
        AXIOS.get('/applicationUsers/'.concat(loginUsername))
        .then(response => {
            this.appUser = response.data
            this.appUser.username = response.data.username
            this.appUser.fullname = response.data.fullname
            this.appUser.address = response.data.address
        })
        .catch(e => {
            this.errorProfile = e
        })

        
    },

}






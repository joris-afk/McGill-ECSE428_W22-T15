import axios from 'axios'
import $ from "jquery"

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

// retrieve user list from backend
var Username = sessionStorage.getItem('currentUsername');

export default {
    name: 'editAccount',
    data(){
      return{
        appUser:{
            username: '',
            fullname: '',
            address: '',
            password: '',
        },         
        appUsers: [],
        errorEditAccount: '',
      }
    },

    created: function() {
      // owl
      $(function(){
        $('#password').focus(function(){
            $('#owl').addClass('password');
        }).blur(function(){
            $('#owl').removeClass('password');
        })
      })
        
        AXIOS.get('/applicationUsers/'.concat(Username))
        .then(response => {
            this.appUsers = response.data
            this.appUsers.username = response.data.username
            this.appUsers.fullname = response.data.fullname
            this.appUsers.address = response.data.address
        })
        .catch(e => {
            this.errorProfile = e
        })
        // // find the login user
        // const retrieveUser = (loginUsername) => {
        //     for(const aUser of this.appUsers){
        //         if(loginUsername.localeCompare(aUser.username) == 0){
        //             this.appUser = aUser
        //         }
        //     }
        // }
        // // function call
        // retrieveUser(Username) 
    },

    methods:{
        updateInfo: function (username, fullname, password, address){
            AXIOS.put('/applicationUsers/'.concat(Username),{},
            {params:{
                new_username: username,
                new_name: fullname,
                new_password: password,
                new_address: address
            }})
            .then(response => {
                sessionStorage.setItem("loginUsername", username);
            })
            .catch(e => {
                var errorMsg = e.response.data.message
                console.log(errorMsg)
                this.errorEditAccount = errorMsg
            })         
        }
    }

}
  

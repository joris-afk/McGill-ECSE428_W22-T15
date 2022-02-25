import axios from 'axios'

var config = require('../../config')

var frontendUrl= 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl}
})

// get data from login page
var loginUsername = sessionStorage.getItem('loginUsername');

var trial; 
  

// pass data to edit account page



export default {
    name: 'profile',
    
    data(){
      return{
        appUser:{
            username: 'user!!!',
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
        trial = this.appUser.username
        console.log(trial);
        sessionStorage.setItem("Username", trial);

        // // retrieve user list from backend
        // AXIOS.get()
        // .then(response => {
        //     this.appUsers = response.data
        // })
        // .catch(e => {
        //     this.errorProfile = e
        // })
        // // find the login user
        // const retrieveUser = (loginUsername) => {
        //     for(const aUser of this.appUsers){
        //         if(loginUsername.localeCompare(aUser.username) == 0){
        //             this.appUser = aUser
        //         }
        //     }
        // }
        // // function call
        // retrieveUser(loginUsername)   

        
    },

}






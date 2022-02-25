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
        Username: sessionStorage.getItem('Username'),
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
        // retrieve user list from backend
        AXIOS.get('/applicationUsers/'.concat(Username))
        .then(response => {
            this.appUser = response.data
        })
        .catch(e => {
            this.errorProfile = e
        })
        // find the login user
        const retrieveUser = (loginUsername) => {
            for(const aUser of this.appUsers){
                if(loginUsername.localeCompare(aUser.username) == 0){
                    this.appUser = aUser
                }
            }
        }
        // function call
        retrieveUser(loginUsername) 
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
  

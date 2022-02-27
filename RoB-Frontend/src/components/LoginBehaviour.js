import $ from "jquery"
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
    name: 'login',
    data(){
        return{
            account:'',
            password:'',
            users:[],
            errorUser: '',
            errorLogin:'',
        }
    },
    created: function(){
        $(function(){
            $('#password').focus(function(){
                $('#owl').addClass('password');
            }).blur(function(){
                $('#owl').removeClass('password');
            })
            
        })
    },
    methods: {
        login: function(account,password){

            this.errorLogin='';
            AXIOS.get('/applicationUsers').then(response => {
                this.users=response.data
            })
            .then(response => {
                for (const user of this.users){
                    if (account.localeCompare(user.username)==0 && password.localeCompare(user.password)==0){
                        // pass data to profile page
                        sessionStorage.setItem("loginUsername", account);
                        // this.$router.push('/profile')
                        window.location.assign("#/profile")
                    }else{
                        this.errorLogin="Account and Password does not match"
                    }
                }
            })
            .catch(e => {
                this.errorUser=e
            })
        }
    }
}
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

$(function(){
    $('#password').focus(function(){
        $('#owl').addClass('password');
    }).blur(function(){
        $('#owl').removeClass('password');
    })
    
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
    methods: {
        login: function(account,password){
            this.errorLogin='';
            AXIOS.get('/applicationUsers').then(response => {
                this.users=response.data
            })
            .catch(e => {
                this.errorUser=e
            })

            const loginVerification = (account, password) => {
                //to be tested, if api does not work this would not work
                for (const user of this.users){
                    if (account.localeCompare(user.username)==0 && password.localeCompare(user.password)==0){
                        this.$router.push('/profile')
                    }else{
                        this.errorLogin="Account and Password does not match"
                    }
                }
            
            }
            loginVerification(account,password)
        }
    }
}
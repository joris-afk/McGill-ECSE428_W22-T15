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

function UserDto(account,fullname,address,password){
    this.account=account
    this.fullname=fullname
    this.address=address
    this.password=password
}

export default{
    name:'user',
    data(){
        return{
            account:'',
            fullname:'',
            address:'',
            password:'',
            users:[],
            newUser:'',
            errorUser:'',
            response:[]
        }
    },
    created: function(){
        AXIOS.get('/applicationUsers/').then(response => {
            this.citizens=response.data
        }).catch(e => {
            this.errorUser=e
        })
        
        $(function(){
            $('#password').focus(function(){
                $('#owl').addClass('password');
            }).blur(function(){
                $('#owl').removeClass('password');
            })
            
        })
    },
    methods: {
        createUser: function(account,fullname,address,password){
            this.errorUser='';
            AXIOS.get('/applicationUsers/').then(response => {
                this.citizens=response.data
            }).catch(e => {
                this.errorUser=e
            })

            for (const user of this.users){
                if (account.localeCompare(user.username)==0){
                    this.errorUser = "Cannot create due to duplicate Account!";
                }
            }
            if(this.errorUser.localeCompare("")==0){
                AXIOS.post('/applicationUsers/'.concat(account),{},
                {params:{
                    fullname:fullname,
                    address:address,
                    password:password
                }}).then(response => {
                    this.users.push(response.data)
                    this.account=''
                    this.fullname=''
                    this.address=''
                    this.password=''
                    this.errorUser=''
                    this.newUser=''
                }).catch(e => {
                    var errorMsg=e.response.data.message
                    console.log(errorMsg)
                    this.errorUser=errorMsg
                })
                this.newUser='';
            }
        }
    }
}
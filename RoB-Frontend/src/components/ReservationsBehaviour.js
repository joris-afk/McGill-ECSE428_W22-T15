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
    name: 'reservation',
    
    data(){
      return{ 
        // get data from login page or edit account info page
        loginUsername: sessionStorage.getItem('currentUsername'),   
        reservationId: "",
        myreservations:[],
        quantity:"",
        errorReservation:""
      }
    },

    created: function() {
        AXIOS.get('/applicationUsers/'.concat(this.loginUsername))
        .then(response => {
            this.myreservations = response.data.myReservations
            console.log(response.data.fullname)
        })
        .catch(e => {
            this.errorProfile = e
        })

        // move to the end when backend is connected
        // pass data to edit account page
        sessionStorage.setItem("currentUsername", this.loginUsername); 
    },

    methods: {
      removeReservation: function(reservationid){
        AXIOS.delete('/reservations/'.concat(reservationid),{},{}
        ).then(response => {
            this.myreservations.map(x => x.reservationId).remove(reservationid)
        }).catch(e => {
            var errorMsg = e.response.data.message
            console.log(errorMsg)
            this.errorItem = errorMsg
        })

        location.reload();
      }
    }

}







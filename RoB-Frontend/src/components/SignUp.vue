<template>
    
    <div class="login-box">
        <div class="owl" id="owl">
            <div class="hand"></div>
            <div class="hand hand-r"></div>
            <div class="arms">
                <div class="arm"></div>
                <div class="arm arm-r"></div>
            </div>
        </div>
        <div class="input-box">
            <input type="text" v-model="account" placeholder="Username/Account">
            <input type="text" v-model="fullname" placeholder="Fullname">
            <input type="text" v-model="address" placeholder="Address">
            <input type="password" v-model="password" placeholder="Password" id="password">
            
            


            <router-link to ="/signUp">
                <button>Sign Up</button>
            </router-link>

            <router-link to ="/">
                <button>Back </button>
            </router-link>

        </div>
         

    </div>


</template>

<script src="./SignUpBehaviour.js">
</script>
<style src="./SignUpStyle.css">
</style>
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
            <input type="text" v-model="account" placeholder="Account">
            <input type="password" v-model="password" placeholder="Password" id="password">

            <button v-bind:disabled="(!account||!password)"
            @click="login(account,password)">Log In</button>

            <router-link to ="/signUp">
                <button>Sign Up</button>
            </router-link>

        </div>
         

    </div>


</template>

<script src="./LoginBehaviour.js">
</script>
<style src="./LoginStyle.css">
</style>
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
            <input type="text" v-model="appUser.username" disabled>              
            <input type="text" v-model="appUser.fullname" placeholder="fullname">              
            <input type="text" v-model="appUser.address" placeholder="address">            
            <input type="password" v-model="appUser.password" placeholder="password" id="password">             
            


                <button v-bind:disabled="(!appUser.username||!appUser.fullname||!appUser.address||!appUser.password)"
                @click="updateInfo(appUser.username, appUser.fullname, appUser.password, appUser.address)">Save Account Info</button>

        </div>
    </div>
</template>

<script src="./EditAccountBehaviour.js"></script>
<style src="./SignUpStyle.css"></style>
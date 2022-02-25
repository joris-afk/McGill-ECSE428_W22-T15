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
            <input type="text" placeholder="Account">
            <input type="password" placeholder="Password" id="password">
            <router-link to ="/profile">
                <button>Log In</button>
            </router-link>

        </div>

    </div>

</template>

<script src="./LoginBehaviour.js">
</script>
<style src="./LoginStyle.css">
</style>
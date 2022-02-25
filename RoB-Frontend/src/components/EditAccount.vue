<template>
    <div>
        <h2> Edit Account Information </h2>

        <table class = "tablestyle" align="center">
            <tr class="trstyle">
                <th class="thstyle">Username</th>
                <td class="tdstyle"><input type="text" v-model="Username" placeholder="username"></td>               
            </tr>
            <tr class="trstyle">
                <th class="thstyle">Fullname</th>
                <td class="tdstyle"><input type="text" v-model="appUser.fullname" placeholder="fullname"></td>               
            </tr>
            <tr class="trstyle">
                <th class="thstyle">Address</th>
                <td class="tdstyle"><input type="text" v-model="appUser.address" placeholder="address"></td>               
            </tr>
            <tr class="trstyle">
                <th class="thstyle">Password</th>
                <td class="tdstyle"><input type="text" v-model="appUser.password" placeholder="password"></td>               
            </tr>

        </table>

        <router-link to ="/profile">
            <button class="buttonStyle" v-bind:disabled="(!Username||!appUser.fullname||!appUser.address||!appUser.password)">Save Account Info</button>
        </router-link>

    </div>
</template>

<script src="./EditAccountBehaviour.js"></script>
<style src="./EditAccountStyle.css"></style>
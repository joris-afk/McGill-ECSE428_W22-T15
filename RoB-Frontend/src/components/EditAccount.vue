<template>
    <div>
        <h2> Edit Account Information </h2>

        <table class = "tablestyle" align="center">
            <tr class="trstyle">
                <th class="thstyle">Username</th>
                <td class="tdstyle">{{Username}}</td>
            </tr>

        </table>

        <router-link to ="/profile">
            <button class="buttonStyle">Save Account Info</button>
        </router-link>

    </div>
</template>

<script src="./EditAccountBehaviour.js"></script>
<style src="./EditAccountStyle.css"></style>
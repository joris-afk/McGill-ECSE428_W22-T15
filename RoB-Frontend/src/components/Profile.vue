<template>
    <div class="profile">
        <!-- welcome section -->
        <section class="welcome">
            <div class="left">
                <h2>Welcome {{appUser.username}}</h2>
            </div>
            <div class="right">
                <router-link to ="/">
                    <button class="buttonStyle">Logout</button>
                </router-link>
            </div>
        </section>

        <!-- account info section -->
        <section class="account">
            <p class="title">Account Information</p>
            <table class = "tablestyle" align="left">
                <tr class="trstyle">
                    <th class="thstyle">Username</th>
                    <td class="tdstyle">{{appUser.username}}</td>
                </tr>
                <tr class="trstyle">
                    <th class="thstyle">Fullname</th>
                    <td class="tdstyle">{{appUser.fullname}}</td>
                </tr>
                <tr class="trstyle">
                    <th class="thstyle">Address</th>
                    <td class="tdstyle">{{appUser.address}}</td>
                </tr>
            </table>

            <div class="account_buttons">
                <router-link to ="/editAccount">
                    <button class="buttonStyle">Edit Account Info</button>
                </router-link>
                <router-link to ="/item">
                    <button class="buttonStyle">My Listed Items</button>
                </router-link>
           </div>

        </section>
        <!-- logout section -->

    </div>


</template>

<script src="./ProfileBehaviour.js"></script>
<style src="./ProfileStyle.css"></style>
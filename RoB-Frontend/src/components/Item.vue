<template>

    <div class="items">

        <section class="header">
            <router-link to ="/profile">
                <button>Back</button>
            </router-link>
        </section>

        <!-- all items -->
        <section class="current_listed_items">
            <p class="title">My Listings</p>
            <table class = "tablestyle" align="left">
                <tr class="trstyle">
                    <th class="thstyle">Item</th>
                    <th class="thstyle">Price</th>
                    <th class="thstyle">Sizes</th>
                </tr>
                <tr class="trstyle" v-for = "item in userItems" :key="item">
                    <td class="tdstyle">{{item.name}}</td>
                    <td class="tdstyle">{{item.price}}</td>
                    <td class="tdstyle">{{item.availableSizes}}</td>
                </tr>
            </table>
        </section>

        <section class="add_item">
            <p class="title">List an Item</p>
            <p class="text">Please indicate the name, price and possible sizes of the item
                you would like to list, then press the button bellow to activate your listing.
            </p>
            <input type="text" v-model="newName" placeholder="item name">              
            <input type="text" v-model="newPrice" placeholder="price">              
            <input type="text" v-model="newAvailableSizes" placeholder="available sizes">            
            
            <button v-bind:disabled="(!newName||!newPrice)"
            @click="createItem(newName,newPrice,newAvailableSizes)">List Item</button>
        </section>

    </div>
</template>

<script src="./ItemBehaviour.js"></script>
<style src="./ItemStyle.css"></style>
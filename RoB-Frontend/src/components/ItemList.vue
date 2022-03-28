<template>

    <div class="itemlists">
        <h2>Existing Products</h2>

        <div class="items" v-for="item in products" :key="item.name">
            <div class="card">
                <table>
                    <td id="section1">
                        <tr>
                            <th class="thstyle">Item Name</th>
                            <td class="tdstyle">{{item.name}}</td>
                        </tr>
                        <tr>
                            <th class="thstyle">Item Price</th>
                            <td class="tdstyle">{{item.price}}</td>
                        </tr>
                        <tr>
                            <th class="thstyle">Available Sizes</th>
                            <td class="tdstyle">{{item.availableSizes}}</td>
                        </tr>
                    </td>
                    <td id="section2">
                        <input type="text" v-model="requiredQuantity" placeholder="required quantity">
                        <input type="text" v-model="reservationId" placeholder="reservation id">
                        <button @click="reserveItem(reservationId, requiredQuantity, item.name)">Reserve</button>
                    </td>
                    <td id="section2">
                        <button @click="addToCart(item.name, item.price, item.availableSizes)">Add To Cart</button>          
                    </td>
                </table>
            </div>
        </div>
        <router-link to ="/profile">
            <button class="buttonStyle">Back to Profile</button>
        </router-link>
        <router-link to ="/cart">
            <button class="buttonStyle">View Cart</button>
        </router-link>
    </div>
</template>

<script src="./ItemListBehaviour.js"></script>
<style src="./ItemListStyle.css"></style>
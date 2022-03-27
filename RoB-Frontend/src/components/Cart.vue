<template>

    <div class="cart">
        <h2>My cart</h2>

        <div class="items" v-for="itemInCart in cart.cartItems" :key="itemInCart.itemInCartId">
            <div class="card">
                <table class="tablestyle">
                    <td class="tdstyle">
                        <tr class="trstyle">
                            <th class="thstyle">Item Name</th>
                            <td class="tdstyle">{{itemInCart.items.name}}</td>
                        </tr>
                        <tr class="trstyle">
                            <th class="thstyle">Item Price</th>
                            <td class="tdstyle">{{itemInCart.items.price}}</td>
                        </tr>
                        <tr class="trstyle">
                            <th class="thstyle">quantity</th>
                            <td class="tdstyle">{{itemInCart.quantity}}</td>
                        </tr>
                        <tr class="trstyle">
                            <th class="thstyle">size</th>
                            <td class="tdstyle">{{itemInCart.size}}</td>
                        </tr>
                    </td>
                </table>
            </div>
        </div>
        <div>
            <input type="text" v-model="orderId" placeholder="order/purchase id">
        </div>
        <div class="buttons">
            <button class="buttonStyle" @click="purchaseItemsInCart(orderId)">Place Order</button>
            <router-link to ="/profile">
                <button class="buttonStyle">Back to Profile</button>
            </router-link>
        </div>

    </div>
</template>

<script src="./CartBehaviour.js"></script>
<style src="./CartStyle.css"></style>
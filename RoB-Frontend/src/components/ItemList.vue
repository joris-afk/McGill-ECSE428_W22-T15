<template>

    <div class="itemlists">
        <h2>Existing Products</h2>

        <div class="items" v-for="item in products" :key="item.name">
            <div class="card">
                <table class="tablestyle">
                    <td class="tdstyle">
                        <tr class="trstyle">
                            <th class="thstyle">Item Name</th>
                            <td class="tdstyle">{{item.name}}</td>
                        </tr>
                        <tr class="trstyle">
                            <th class="thstyle">Item Price</th>
                            <td class="tdstyle">{{item.price}}</td>
                        </tr>
                        <tr class="trstyle">
                            <th class="thstyle">Available Sizes</th>
                            <td class="tdstyle">{{item.availableSizes}}</td>
                        </tr>
                    </td>
                    <td class="tdstyle">
                        <input type="text" v-model="requiredQuantity" placeholder="required quantity">
                        <input type="text" v-model="reservationId" placeholder="reservation id">
                        <td><button @click="reserveItem(reservationId, requiredQuantity, item.name)">Reserve</button></td>
                    </td>
                    <!-- button for add item to cart but not functioning yet -->
                    <td class="tdstyle">
                            <td><button> Add To Cart</button></td>            
                    </td>

                </table>
            </div>
        </div>
    </div>
</template>

<script src="./ItemListBehaviour.js"></script>
<style src="./ItemListStyle.css"></style>
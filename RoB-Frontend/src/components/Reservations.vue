<template>
    <div class="reservation">
        <section class="info">
            <p class="title">Reservations Information</p>
            <div class=tbl align="center">
            <table class = "tablestyle" align="center">
                <tr class="trstyle">
                    <th class="thstyle">ID</th>
                    <th class="thstyle">Item</th>
                    <th class="thstyle">Quantity</th>
                </tr>
                <tr class="trstyle" v-for = "reservation in myreservations" :key="reservation">
                    <td class="tdstyle">{{reservation.reservationId}}</td>
                    <td class="tdstyle">{{reservation.item.name}}</td>
                    <td class="tdstyle">{{reservation.quantity}}</td>
                    <td class="tdstyle">
                        <button @click="removeReservation(reservation.reservationId)">Delete</button>
                    </td>
                </tr>

               
            </table>

            <div class="reservation_buttons">
                <router-link to ="/profile">
                    <button class="buttonStyle">Back to Profile</button>
                </router-link>

           </div>
    </div>
        </section>
        
    </div>
</template>

<script src="./ReservationsBehaviour.js"></script>
<style src="./ReservationsStyle.css"></style>
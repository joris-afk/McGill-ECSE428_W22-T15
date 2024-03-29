import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import Profile from '@/components/Profile'
import EditAccount from '@/components/EditAccount'
import SignUp from '@/components/SignUp'
import Item from '@/components/Item'
import Reservation from '@/components/Reservations'
import ItemList from '@/components/ItemList'
import Cart from '@/components/Cart'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login
    },
    {
      path: '/profile',
      name: 'Profile',
      component: Profile
    },
    {
      path: '/editAccount',
      name: 'EditAccount',
      component: EditAccount
    },
    {
      path: '/signUp',
      name: 'SignUp',
      component: SignUp
    },
    {
      path: '/item',
      name: 'Item',
      component: Item
    },
    {
      path: '/reservation',
      name: 'reservation',
      component: Reservation
    },
    {
      path: '/itemlist',
      name: 'Itemlist',
      component: ItemList
    },
    {
      path: '/cart',
      name: 'Cart',
      component: Cart
    }
  ]
})

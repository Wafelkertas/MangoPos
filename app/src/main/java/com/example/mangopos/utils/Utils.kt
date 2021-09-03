package com.example.mangopos.utils

import android.util.Log
import com.example.mangopos.data.objects.dto.*


fun menuToCartItem(menu: MenuItem, cartUuid: String): Cart {
    return (
            Cart(
                image = "",
                menuName = menu.name,
                price = menu.price,
                menuUuid = menu.uuid,
                quantity = 1,
                uuid = "",
                createdAt = "",
                id = 0,
                orderUuid = cartUuid,
                updatedAt = ""
            )
            )
}

fun addItemToList(list:List<Cart>,  oldItem:Cart) :List<Cart> {

    val newItem = Cart(
        createdAt = oldItem.createdAt,
        id =  oldItem.id,
        image =  oldItem.image,
        menuName =  oldItem.menuName,
        menuUuid =  oldItem.menuUuid,
        orderUuid =  oldItem.createdAt,
        price =  oldItem.price,
        quantity =  oldItem.quantity+1,
        updatedAt =  oldItem.updatedAt,
        uuid =  oldItem.uuid

    )
    val position = list.indexOf(oldItem)

    Log.d("quantity", newItem.quantity.toString())
    val newList = list.filterNot { data ->
        data == oldItem
    } as MutableList
    newList.add(position, newItem)
    return newList
}

fun removeItemToList(list:List<Cart>,  oldItem:Cart) :List<Cart> {

    val newItem = Cart(
        createdAt = oldItem.createdAt,
        id =  oldItem.id,
        image =  oldItem.image,
        menuName =  oldItem.menuName,
        menuUuid =  oldItem.menuUuid,
        orderUuid =  oldItem.createdAt,
        price =  oldItem.price,
        quantity =  oldItem.quantity-1,
        updatedAt =  oldItem.updatedAt,
        uuid =  oldItem.uuid

    )
    val position = list.indexOf(oldItem)



    Log.d("quantity", newItem.quantity.toString())
    val newList = list.filterNot { data ->
        data == oldItem
    } as MutableList

    newList.add(position, newItem)
    return newList
}


fun deleteItemFromList(list:List<Cart>,  oldItem:Cart) :List<Cart> {


    val newList = list.filterNot { data ->
    Log.d("filtering", "${oldItem.menuUuid}, ${data.menuUuid}")
        data.menuUuid == oldItem.menuUuid
    }
    return newList
}







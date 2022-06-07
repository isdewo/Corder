package com.example.corder

import android.graphics.drawable.Drawable

data class ListData(var imgUrl: String, var cafeCode: String ?= null, var cafeName: String ?= null, var ownerName: String ?= null, var cafeAddress: String ?= null, var cafeCall: String ?= null, var cafeTime: String ?= null, var limitPrice: Int ?= 0) {
}

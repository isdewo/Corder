package com.example.corder

data class ListData(var imgUri: String,
                    var cafeCode: String ?= null,
                    var cafeName: String ?= null,
                    var ownerName: String ?= null,
                    var cafeAddress: String ?= null,
                    var cafeCall: String ?= null,
                    var cafeTime: String ?= null,
                    var limitPrice: Int ?= 0,
                    var menu: ArrayList<ListMenu> ?= null) {
}

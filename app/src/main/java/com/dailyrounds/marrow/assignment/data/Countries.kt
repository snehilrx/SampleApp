package com.dailyrounds.marrow.assignment.data

data class Countries(
    var data : Data = Data()
)

data class Data(
    val countryRegionMap : Map<String, CountryRegion> = emptyMap()
)

data class CountryRegion(
    val country: String? = null,
    val region: String? = null
)

data class Country(
    val countryCode: String? = null,
    val country: String? = null,
    val region: String? = null
)
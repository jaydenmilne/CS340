import com.google.gson.annotations.SerializedName

enum class MaterialType(val material: String) {
    @SerializedName("reality_shard") REALITY_SHARD("reality_shard"),
    @SerializedName("soul_shard") SOUL_SHARD("soul_shard"),
    @SerializedName("space_shard") SPACE_SHARD("space_shard"),
    @SerializedName("mind_shard") MIND_SHARD("mind_shard"),
    @SerializedName("power_shard") POWER_SHARD("power_shard"),
    @SerializedName("time_shard") TIME_SHARD("time_shard"),
    @SerializedName("vibranium") VIBRANIUM("vibranium"),
    @SerializedName("palladium") PALLADIUM("palladium"),
    @SerializedName("infinity_gauntlet") INFINITY_GAUNTLET("infinity_gauntlet");

    companion object {
        fun matchesRouteType(routeType: RouteType, materialType: MaterialType): Boolean {

            // Infinity Gauntlet matches any route type
            if (materialType == INFINITY_GAUNTLET) {
                return true
            }

            return when (routeType) {
                // Any card matches route type
                RouteType.ANY -> true
                RouteType.REALITY -> materialType == REALITY_SHARD
                RouteType.SOUL -> materialType == SOUL_SHARD
                RouteType.SPACE -> materialType == SPACE_SHARD
                RouteType.MIND -> materialType == MIND_SHARD
                RouteType.POWER -> materialType == POWER_SHARD
                RouteType.TIME -> materialType == TIME_SHARD
                RouteType.VIBRANIUM -> materialType == VIBRANIUM
                RouteType.PALLADIUM -> materialType == PALLADIUM
            }
        }
    }

}
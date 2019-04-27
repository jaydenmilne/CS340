import java.io.Serializable

enum class RouteType(val type: String) : Serializable {
    ANY("any"),
    REALITY("reality"),
    SOUL("soul"),
    SPACE("space"),
    MIND("mind"),
    POWER("power"),
    TIME("time"),
    VIBRANIUM("vibranium"),
    PALLADIUM("palladium");

    fun getMaterialType(): MaterialType? {
        return when (this) {
            REALITY -> MaterialType.REALITY_SHARD
            SOUL -> MaterialType.SOUL_SHARD
            SPACE -> MaterialType.SPACE_SHARD
            MIND -> MaterialType.MIND_SHARD
            POWER -> MaterialType.POWER_SHARD
            TIME -> MaterialType.TIME_SHARD
            VIBRANIUM -> MaterialType.VIBRANIUM
            PALLADIUM -> MaterialType.PALLADIUM
            else -> null
        }
    }
}
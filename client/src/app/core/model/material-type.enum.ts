export enum MaterialType {
    REALITY_SHARD = 'reality_shard',
    SOUL_SHARD = 'soul_shard',
    SPACE_SHARD = 'space_shard',
    MIND_SHARD = 'mind_shard',
    POWER_SHARD = 'power_shard',
    TIME_SHARD = 'time_shard',
    VIBRANIUM = 'vibranium',
    PALLADIUM = 'palladium',
    INFINITY_GAUNTLET = 'infinity_gauntlet',
    ANY = 'any'
}

export function getMaterialTypeDisplayName(type: MaterialType) : string {
    switch(type) {
    case MaterialType.REALITY_SHARD:
        return "Reality Stone Shard";
    case MaterialType.SOUL_SHARD:
        return "Soul Stone Shard";
    case MaterialType.SPACE_SHARD:
        return "Space Stone Shard";
    case MaterialType.MIND_SHARD:
        return "Mind Stone Shard";
    case MaterialType.POWER_SHARD:
        return "Power Stone Shard";
    case MaterialType.TIME_SHARD:
        return "Time Stone Shard";
    case MaterialType.VIBRANIUM:
        return "Vibranium Shard";
    case MaterialType.PALLADIUM:
        return "Palladium Shard";
    case MaterialType.INFINITY_GAUNTLET:
        return "Infinity Gauntlet";
    case MaterialType.ANY:
        return "Any";
    }
}
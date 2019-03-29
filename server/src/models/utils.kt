package models

class DisplayNameFormatter {

    fun getProperCityName(sanitizedName: String): String {
        when (sanitizedName) {
            "darkdimension" -> return "Dark Dimension"
            "gibborim" -> return "Gibborim"
            "galactus" -> return "Galactus"
            "zennla" -> return "Zenn-La"
            "knowhere" -> return "Knowhere"
            "chitaurisanctuary" -> return "Chitauri Sanctuary"
            "zenwhoberi" -> return "Zen-Whoberi"
            "ego" -> return "Ego"
            "kitson" -> return "Kitson"
            "caveofthedragon" -> return "Cave of the Dragon"
            "kunlun" -> return "K\'un-Lun"
            "vanaheim" -> return "Vanaheim"
            "titan" -> return "Titan"
            "vormir" -> return "Vormir"
            "sakaar" -> return "Sakaar"
            "xandar" -> return "Xandar"
            "caveofages" -> return "Cave of Ages"
            "surturslair" -> return "Surtur\'s Lair"
            "hala" -> return "Hala"
            "svartlheim" -> return "Svartlheim"
            "niflheim" -> return "Niflheim"
            "contraxia" -> return "Contraxia"
            "quantumrealm" -> return "Quantum Realm"
            "asgard" -> return "Asgard"
            "nidavellir" -> return "Nidavellir"
            "yotunheim" -> return "Yotunheim"
            "hongkongsanctum" -> return "Hong Kong Sanctum"
            "wakanda" -> return "Wakanda"
            "sokovia" -> return "Sokovia"
            "pymlabs" -> return "Pym Labs"
            "avengershq" -> return "Avenger\'s HQ"
            "newyorkcity" -> return "New York City"
            "triskelion" -> return "Triskelion"
            "helicarrier" -> return "Helicarrier"
            "kamartaj" -> return "Kamar-Taj"
            "muspelheim" -> return "Muspelheim"
        }
        return ""
    }
}
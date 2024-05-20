package net.mandomc.mandomcremade.utility;

import net.mandomc.mandomcremade.config.SaberConfig;
import net.mandomc.mandomcremade.config.WarpConfig;

public class MatrixHolders {

    public static Object[][] getLightSaberMatrix(){
        Object[][] object = new Object[4][4];

        object[0][0] = "lukeSkywalker";
        object[0][1] = "&3Luke Skywalker's Lightsaber";
        object[0][2] = SaberConfig.get().getDouble("LukeSkywalker");
        object[0][3] = 1;

        object[1][0] = "anakinSkywalker";
        object[1][1] = "&3Anakin Skywalker's Lightsaber";
        object[1][2] = SaberConfig.get().getDouble("AnakinSkywalker");
        object[1][3] = 2;

        return object;
    }

    public static String getLightSaberName(String character){

        Object[][] object = getLightSaberMatrix();
        String name = "";

        for (Object[] objects : object) {
            if (objects[0].equals(character)) {
                name = (String) objects[1];
            }}
        return name;
    }

    public static Double getLightSaberDamage(String character){

        Object[][] object = getLightSaberMatrix();
        Double damage = 0.0;

        for (Object[] objects : object) {
            if (objects[0].equals(character)) {
                damage = (Double) objects[2];
            }}
        return damage;
    }

    public static int getLightSaberCustomModelData(String character){

        Object[][] object = getLightSaberMatrix();
        int customModelData = 0;

        for (Object[] objects : object) {
            if (objects[0].equals(character)) {
                customModelData = (int) objects[3];
            }}
        return customModelData;
    }

    public static Object[][] getHiltMatrix(){
        Object[][] object = new Object[4][3];

        object[0][0] = "lukeSkywalker";
        object[0][1] = "&3Luke Skywalker's Hilt";
        object[0][2] = 1;

        object[1][0] = "anakinSkywalker";
        object[1][1] = "&3Anakin Skywalker's Hilt";
        object[1][2] = 2;

        return object;
    }

    public static String getHiltName(String character){

        Object[][] object = getHiltMatrix();
        String name = "";

        for (Object[] objects : object) {
            if (objects[0].equals(character)) {
                name = (String) objects[1];
            }}
        return name;
    }

    public static int getHiltCustomModelData(String character){

        Object[][] object = getHiltMatrix();
        int customModelData = 0;

        for (Object[] objects : object) {
            if (objects[0].equals(character)) {
                customModelData = (int) objects[2];
            }}
        return customModelData;
    }

    public static Object[][] getKyberMatrix(){
        Object[][] object = new Object[3][2];

        object[0][0] = "red";
        object[0][1] = "&cRed Kyber Crystal";
        object[0][2] = 1;

        object[1][0] = "blue";
        object[1][1] = "&1Blue Kyber Crystal";
        object[1][2] = 2;

        object[2][0] = "green";
        object[2][1] = "&2Green Kyber Crystal";
        object[2][2] = 3;

        return object;
    }

    public static String getKyberName(String character){

        Object[][] object = getKyberMatrix();
        String name = "";

        for (Object[] objects : object) {
            if (objects[0].equals(character)) {
                name = (String) objects[1];
            }}
        return name;
    }

    public static int getKyberCustomModelData(String character){

        Object[][] object = getKyberMatrix();
        int customModelData = 0;

        for (Object[] objects : object) {
            if (objects[0].equals(character)) {
                customModelData = (int) objects[2];
            }}
        return customModelData;
    }

    public static Object[][] getWarpsMatrix(){
        Object[][] object = new Object[14][11];

        object[0][0] = "dathomir";
        object[0][1] = WarpConfig.get().getInt("DathomirSlot");
        object[0][2] = WarpConfig.get().getString("DathomirName");
        object[0][3] = WarpConfig.get().getString("DathomirDesc");
        object[0][4] = WarpConfig.get().getDouble("DathomirX");
        object[0][5] = WarpConfig.get().getDouble("DathomirY");
        object[0][6] = WarpConfig.get().getDouble("DathomirZ");
        object[0][7] = (float) WarpConfig.get().getDouble("DathomirYaw");
        object[0][8] = (float) WarpConfig.get().getDouble("DathomirPitch");
        object[0][9] = WarpConfig.get().getInt("DathomirRank");
        object[0][10] =  WarpConfig.get().getItemStack("DathomirBlock");

        object[1][0] = "mustafar";
        object[1][1] = WarpConfig.get().getInt("MustafarSlot");
        object[1][2] = WarpConfig.get().getString("MustafarName");
        object[1][3] = WarpConfig.get().getString("MustafarDesc");
        object[1][4] = WarpConfig.get().getDouble("MustafarX");
        object[1][5] = WarpConfig.get().getDouble("MustafarY");
        object[1][6] = WarpConfig.get().getDouble("MustafarZ");
        object[1][7] = (float) WarpConfig.get().getDouble("MustafarYaw");
        object[1][8] = (float) WarpConfig.get().getDouble("MustafarPitch");
        object[1][9] = WarpConfig.get().getInt("MustafarRank");
        object[1][10] =  WarpConfig.get().getItemStack("MustafarBlock");

        object[2][0] = "earth";
        object[2][1] = WarpConfig.get().getInt("EarthSlot");
        object[2][2] = WarpConfig.get().getString("EarthName");
        object[2][3] = WarpConfig.get().getString("EarthDesc");
        object[2][4] = WarpConfig.get().getDouble("EarthX");
        object[2][5] = WarpConfig.get().getDouble("EarthY");
        object[2][6] = WarpConfig.get().getDouble("EarthZ");
        object[2][7] = (float) WarpConfig.get().getDouble("EarthYaw");
        object[2][8] = (float) WarpConfig.get().getDouble("EarthPitch");
        object[2][9] = WarpConfig.get().getInt("EarthRank");
        object[2][10] =  WarpConfig.get().getItemStack("EarthBlock");

        object[3][0] = "umbara";
        object[3][1] = WarpConfig.get().getInt("UmbaraSlot");
        object[3][2] = WarpConfig.get().getString("UmbaraName");
        object[3][3] = WarpConfig.get().getString("UmbaraDesc");
        object[3][4] = WarpConfig.get().getDouble("UmbaraX");
        object[3][5] = WarpConfig.get().getDouble("UmbaraY");
        object[3][6] = WarpConfig.get().getDouble("UmbaraZ");
        object[3][7] = (float) WarpConfig.get().getDouble("UmbaraYaw");
        object[3][8] = (float) WarpConfig.get().getDouble("UmbaraPitch");
        object[3][9] = WarpConfig.get().getInt("UmbaraRank");
        object[3][10] =  WarpConfig.get().getItemStack("UmbaraBlock");

        object[4][0] = "alderaan";
        object[4][1] = WarpConfig.get().getInt("AlderaanSlot");
        object[4][2] = WarpConfig.get().getString("AlderaanName");
        object[4][3] = WarpConfig.get().getString("AlderaanDesc");
        object[4][4] = WarpConfig.get().getDouble("AlderaanX");
        object[4][5] = WarpConfig.get().getDouble("AlderaanY");
        object[4][6] = WarpConfig.get().getDouble("AlderaanZ");
        object[4][7] = (float) WarpConfig.get().getDouble("AlderaanYaw");
        object[4][8] = (float) WarpConfig.get().getDouble("AlderaanPitch");
        object[4][9] = WarpConfig.get().getInt("AlderaanRank");
        object[4][10] = WarpConfig.get().getItemStack("AlderaanBlock");

        object[5][0] = "concordia";
        object[5][1] = WarpConfig.get().getInt("ConcordiaSlot");
        object[5][2] = WarpConfig.get().getString("ConcordiaName");
        object[5][3] = WarpConfig.get().getString("ConcordiaDesc");
        object[5][4] = WarpConfig.get().getDouble("ConcordiaX");
        object[5][5] = WarpConfig.get().getDouble("ConcordiaY");
        object[5][6] = WarpConfig.get().getDouble("ConcordiaZ");
        object[5][7] = (float) WarpConfig.get().getDouble("ConcordiaYaw");
        object[5][8] = (float) WarpConfig.get().getDouble("ConcordiaPitch");
        object[5][9] = WarpConfig.get().getInt("ConcordiaRank");
        object[5][10] = WarpConfig.get().getItemStack("ConcordiaBlock");

        object[6][0] = "kashyyyk";
        object[6][1] = WarpConfig.get().getInt("KashyyykSlot");
        object[6][2] = WarpConfig.get().getString("KashyyykName");
        object[6][3] = WarpConfig.get().getString("KashyyykDesc");
        object[6][4] = WarpConfig.get().getDouble("KashyyykX");
        object[6][5] = WarpConfig.get().getDouble("KashyyykY");
        object[6][6] = WarpConfig.get().getDouble("KashyyykZ");
        object[6][7] = (float) WarpConfig.get().getDouble("KashyyykYaw");
        object[6][8] = (float) WarpConfig.get().getDouble("KashyyykPitch");
        object[6][9] = WarpConfig.get().getInt("KashyyykRank");
        object[6][10] = WarpConfig.get().getItemStack("KashyyykBlock");

        return object;
    }

    public static int getWarpSlot(String character){

        Object[][] object = getKyberMatrix();
        int slot = 0;

        for (Object[] objects : object) {
            if (objects[0].equals(character)) {
                slot = (int) objects[1];
            }}
        return slot;
    }

    public static String getWarpName(String character){

        Object[][] object = getKyberMatrix();
        String name = "";

        for (Object[] objects : object) {
            if (objects[0].equals(character)) {
                name = (String) objects[2];
            }}
        return name;
    }

    public static String getWarpDesc(String character){

        Object[][] object = getKyberMatrix();
        String name = "";

        for (Object[] objects : object) {
            if (objects[0].equals(character)) {
                name = (String) objects[3];
            }}
        return name;
    }

}

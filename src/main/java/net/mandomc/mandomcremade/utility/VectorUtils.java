package net.mandomc.mandomcremade.utility;

import org.bukkit.util.Vector;

public class VectorUtils {

    public static double calculateAngle(Vector vectorA, Vector vectorB) {
        // Normalize the vectors to avoid scale affecting the calculation
        vectorA = vectorA.clone().normalize();
        vectorB = vectorB.clone().normalize();

        // Calculate the dot product
        double dotProduct = vectorA.dot(vectorB);

        // Ensure the value is clamped between -1 and 1 to avoid NaN due to precision errors
        double clampedValue = Math.max(-1.0, Math.min(1.0, dotProduct));

        // Calculate the angle in radians
        return Math.acos(clampedValue);
    }
}

package top.cmarco.aequitas.data.types;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public final class VelocityData {

    private final List<VelocityCopy> velocityCopies = new LinkedList<>();

    public void addVelocityEntry(final double x, final double y, final double z) {
        velocityCopies.add(new VelocityCopy(x, y, z, (x * x) + (z * z), Math.abs(y)));
    }

    public void removeOldVelocities() {
        Iterator<VelocityCopy> velocityCopyIterator = this.velocityCopies.iterator();
        while (velocityCopyIterator.hasNext()) {
            final VelocityCopy velocityCopy = velocityCopyIterator.next();

            if (velocityCopy.getConstructionTime() + 2000L >= System.currentTimeMillis()) {
                continue;
            }

            velocityCopyIterator.remove();
        }
    }

    // Get the highest horizontal velocity
    public double getMaxHorizontal() {
        double greatest = 1d;
        for (final VelocityCopy velocityCopy : this.velocityCopies) {
            final double copyHorizontalVelocity = velocityCopy.getHorizontalVelocity();
            if (greatest < copyHorizontalVelocity) {
                greatest = copyHorizontalVelocity;
            }
        }

        return greatest;
    }

    // Get the highest vertical velocity
    public double getMaxVertical() {
        double greatest = 1d;
        for (final VelocityCopy velocityCopy : this.velocityCopies) {
            final double copyVerticalVelocity = velocityCopy.getVerticalVelocity();
            if (greatest < copyVerticalVelocity) {
                greatest = copyVerticalVelocity;
            }
        }

        return greatest;
    }

}

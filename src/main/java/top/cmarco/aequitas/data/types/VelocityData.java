/*
 *     Aequitas - Anticheat for SpigotMC Servers
 *     Copyright © 2024 CMarco
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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

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

public final class VelocityCopy {

    private final double x, y, z, horizontalVelocity, verticalVelocity;
    private final long constructionTime = System.currentTimeMillis();

    public VelocityCopy(double x, double y, double z, double horizontalVelocity, double verticalVelocity) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.horizontalVelocity = horizontalVelocity;
        this.verticalVelocity = verticalVelocity;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double getHorizontalVelocity() {
        return horizontalVelocity;
    }

    public double getVerticalVelocity() {
        return verticalVelocity;
    }

    public long getConstructionTime() {
        return constructionTime;
    }
}

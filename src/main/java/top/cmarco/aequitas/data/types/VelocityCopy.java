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

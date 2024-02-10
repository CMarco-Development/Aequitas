package top.cmarco.aequitas.data.types;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public final class VelocityCopy {

    private final double x, y, z, horizontalVelocity, verticalVelocity;
    private final long constructionTime = System.currentTimeMillis();
}

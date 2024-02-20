package top.cmarco.aequitas.permissions;

import org.jetbrains.annotations.NotNull;

public enum AequitasPerm {

    ALERTS("aequitas.alerts"),
    FLAGS("aequitas.flags")
    ;

    private final String node;

    AequitasPerm(@NotNull final String node) {
        this.node = node;
    }

    @NotNull
    public String getNode() {
        return node;
    }
}

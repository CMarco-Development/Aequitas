package top.cmarco.aequitas.data.containers;

import top.cmarco.aequitas.checks.CheckInfo;

public final class AlertContainer {

    private final long alertTime;
    private final CheckInfo checkInfo;

    public AlertContainer(long alertTime, CheckInfo checkInfo) {
        this.alertTime = alertTime;
        this.checkInfo = checkInfo;
    }

    public CheckInfo getCheckInfo() {
        return checkInfo;
    }

    public long getAlertTime() {
        return alertTime;
    }
}

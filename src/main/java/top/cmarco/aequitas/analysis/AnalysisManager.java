package top.cmarco.aequitas.analysis;

/**
 * Manager class for analyzing incoming and outgoing packets.
 */
public final class AnalysisManager {

    public AnalysisManager() {}

    /**
     * Analyzer for incoming packets.
     * Client -> Server.
     */
    private final IncomingPacketAnalyser incomingPacketAnalyser = new IncomingPacketAnalyser();

    /**
     * Analyzer for outgoing packets.
     * Server -> Client.
     */
    private final OutgoingPacketAnalyser outgoingPacketAnalyser = new OutgoingPacketAnalyser();

    public IncomingPacketAnalyser getIncomingPacketAnalyser() {
        return incomingPacketAnalyser;
    }

    public OutgoingPacketAnalyser getOutgoingPacketAnalyser() {
        return outgoingPacketAnalyser;
    }
}
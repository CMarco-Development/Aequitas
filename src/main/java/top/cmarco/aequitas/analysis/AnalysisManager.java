package top.cmarco.aequitas.analysis;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Manager class for analyzing incoming and outgoing packets.
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public final class AnalysisManager {

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
}
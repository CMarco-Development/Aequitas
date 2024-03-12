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
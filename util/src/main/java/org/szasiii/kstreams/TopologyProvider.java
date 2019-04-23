package org.szasiii.kstreams;

import org.apache.kafka.streams.Topology;

public interface TopologyProvider {

    Topology createTopology();
}

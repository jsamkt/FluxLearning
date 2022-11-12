package com.company;

import com.hazelcast.config.Config;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class Main {

    public static void main(String[] args) {
        Config config = new Config();
        config.setClusterName("Test_cluster_name");
        NetworkConfig networkConfig = new NetworkConfig()
                .setPort(9898)
                .setPortAutoIncrement(false);
        HazelcastInstance server = Hazelcast.newHazelcastInstance();


    }

}

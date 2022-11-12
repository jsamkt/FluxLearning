package com.company

import com.hazelcast.config.Config
import com.hazelcast.core.Hazelcast

fun main() {
    val config = with(Config()) {
        networkConfig
            .setPort(5701)
            .setPortCount(3)
            .setPortAutoIncrement(true)
        this
    }
    Hazelcast.newHazelcastInstance(config);
}

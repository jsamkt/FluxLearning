package com.company

import com.hazelcast.client.HazelcastClient
import com.hazelcast.client.config.ClientConfig

fun main() {
    val config = ClientConfig()

    val client = HazelcastClient.newHazelcastClient(config)

    val executorService = client.getExecutorService("executor")
    val map = client.getMap<String, String>("map")

    map.put("One", "Ivan")
    map.put("Two", "Nokolay")
    map.put("Three", "Vladimir")
    map.put("Four", "Olga")

    executorService.submitToAllMembers(Subtask())
}
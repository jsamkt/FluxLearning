package com.company

import com.hazelcast.core.HazelcastInstance
import com.hazelcast.core.HazelcastInstanceAware
import java.io.Serializable
import java.util.concurrent.Callable

class Subtask : Callable<String>, Serializable, HazelcastInstanceAware {

    lateinit var hz: HazelcastInstance

    override fun call(): String {
        with(hz.getMap<String, String>("map")) {
            localKeySet()
                .forEach {
                    println(String.format("handles key: %s; value: %s", it, get(it)))
                }
        }
        return ""
    }

    override fun setHazelcastInstance(hz: HazelcastInstance?) {
        this.hz = hz!!
    }
}
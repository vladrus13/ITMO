class ConsistentHashImpl<K> : ConsistentHash<K> {

    private val cycle: ArrayList<Amen> = ArrayList()
    private var countOfShardes = 0

    /**
     * Get position on cycle array in which range we get
     * Return -1, if we don't have any ranges
     */
    private fun getRange(hashcode: Int): Int {
        if (cycle.size == 0) return -1
        var l = 0
        var r = cycle.size - 1
        var m: Int
        while (1 < r - l) {
            m = (l + r) / 2
            if (hashcode > cycle[m].hashcode) l = m else r = m
        }
        return if (hashcode <= cycle[l].hashcode) {
            l
        } else {
            if (hashcode <= cycle[r].hashcode) {
                r
            } else {
                0
            }
        }
    }

    override fun getShardByKey(key: K): Shard = cycle[getRange(key.hashCode())].shard

    private fun getMap(map: HashMap<Shard, HashSet<HashRange>>, position: Int): HashSet<HashRange> {
        val ranges: HashSet<HashRange>? = map[cycle[position].shard]
        val real: HashSet<HashRange>
        if (ranges == null) {
            real = HashSet()
            map[cycle[position].shard] = real
        } else {
            real = ranges
        }
        return real
    }

    private fun clean(map : HashMap<Shard, HashSet<HashRange>>) {
        val keys : HashSet<Shard> = HashSet()
        for (it in map.entries) {
            if (it.value.size == 0) {
                keys.add(it.key)
            }
        }
        for (it in keys) {
            map.remove(it)
        }
    }

    override fun addShard(newShard: Shard, vnodeHashes: Set<Int>): Map<Shard, Set<HashRange>> {
        val map: HashMap<Shard, HashSet<HashRange>> = HashMap()
        val listOfNodes: ArrayList<Int> = ArrayList(vnodeHashes)
        listOfNodes.sortWith { i1, i2 -> i1.compareTo(i2) }
        if (countOfShardes == 0) {
            for (hash in listOfNodes) {
                cycle.add(Amen(newShard, hash))
            }
            countOfShardes++
            return map;
        }
        var iterator = 0
        var lastHash = if (cycle[0].hashcode == 0) 0 else cycle[cycle.size - 1].hashcode
        var position = 0
        var startEat: Int
        var back : Int? = null
        if (cycle[cycle.size - 1].hashcode < listOfNodes[listOfNodes.size - 1]) {
            var backPosition = listOfNodes.size - 1
            startEat = cycle[cycle.size - 1].hashcode + 1
            while (backPosition >= 0 && cycle[cycle.size - 1].hashcode < listOfNodes[backPosition]) {
                backPosition--
            }
            back = backPosition
            while (listOfNodes[iterator] < cycle[position].hashcode) {
                cycle.add(position, Amen(newShard, listOfNodes[iterator]))
                iterator++
                position++
            }
            val set = getMap(map, position)
            if (position > 0) {
                set.add(HashRange(startEat, cycle[position - 1].hashcode))
                lastHash = cycle[0].hashcode
            } else {
                set.add(HashRange(startEat, listOfNodes[listOfNodes.size - 1]))
            }
        }
        while (position < cycle.size) {
            if (listOfNodes[iterator] < cycle[position].hashcode) {
                startEat = lastHash + 1

                val set = getMap(map, position)
                while (listOfNodes.size > iterator && listOfNodes[iterator] < cycle[position].hashcode) {
                    cycle.add(position, Amen(newShard, listOfNodes[iterator]))
                    position++
                    iterator++
                }
                set.add(HashRange(startEat, cycle[position - 1].hashcode))
                if (listOfNodes.size <= iterator) {
                    break
                }
            } else {
                lastHash = cycle[position].hashcode
                position++
            }
        }
        if (back != null) {
            for (it in back + 1 until listOfNodes.size) {
                cycle.add(cycle.size, Amen(newShard, listOfNodes[it]))
            }
        }
        map.remove(newShard)
        countOfShardes++
        clean(map)
        return map
    }

    override fun removeShard(shard: Shard): Map<Shard, Set<HashRange>> {
        val map: HashMap<Shard, HashSet<HashRange>> = HashMap()
        if (countOfShardes == 1) {
            cycle.clear()
            return map
        }
        var startPosition = 0
        while (cycle[startPosition].shard == shard) {
            startPosition++
        }
        var position = startPosition - 1
        if (position == -1) {
            position = cycle.size - 1
        }
        var lastReal = getMap(map, startPosition)
        var hashRangeGiven: Int? = null
        val hashOfStart = cycle[startPosition].hashcode
        while (cycle[position].hashcode != hashOfStart) {
            if (cycle[position].shard == shard) {
                if (hashRangeGiven == null) {
                    hashRangeGiven = cycle[position].hashcode
                }
                cycle.remove(cycle[position])
            } else {
                if (hashRangeGiven != null) {
                    lastReal.add(HashRange(cycle[position].hashcode + 1, hashRangeGiven))
                }
                hashRangeGiven = null
                lastReal = getMap(map, position)
            }
            position--
            if (position == -1) {
                position = cycle.size - 1
            }
        }
        if (hashRangeGiven != null) {
            lastReal.add(HashRange(cycle[position].hashcode + 1, hashRangeGiven))
        }
        countOfShardes--
        clean(map)
        return map
    }
}

class Amen(val shard: Shard, val hashcode: Int)
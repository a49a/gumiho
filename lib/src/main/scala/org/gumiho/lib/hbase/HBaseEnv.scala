package org.gumiho.lib.hbase

import org.apache.hadoop.hbase.{HBaseConfiguration, TableName}
import org.apache.hadoop.hbase.client.{ConnectionFactory, Get, Put}
import org.apache.hadoop.hbase.util.Bytes

class HBaseEnv {
    private val conn = {
        val conf = HBaseConfiguration.create()
        conf.set("hadoop.home.dir", "/opt/hadoop")
        conf.set("hbase.zookeeper.property.clientPort", "2181")
        conf.set("hbase.zookeeper.quorum", "localhost")
        conf.set("hbase.master", "localhost:60010")
        conf.set("hbase.client.pause", "500")
        conf.set("hbase.client.retries.number", "500")
        conf.set("hbase.rpc.timeout", "20000000")
        conf.set("hbase.client.operation.timeout", "300000")
        val connection = ConnectionFactory.createConnection(conf)
        connection
    }

    private val admin = conn.getAdmin()

    def isExistTable(tableName: String) = {
        admin.tableExists(TableName.valueOf(tableName))
    }

    def addRow(tableName: String,
               rowKey: String,
               cf: String,
               cn: String,
               value: String) = {
        val table = conn.getTable(TableName.valueOf(tableName))
        val put = new Put(Bytes.toBytes(rowKey))
        put.addColumn(Bytes.toBytes(cf), Bytes.toBytes(cn), Bytes.toBytes(value))
        table.put(put)
        table.close()
    }

    def getRow(tableName: String,
               rowKey: String,
               cf: String,
               cn: String) = {
        val table = conn.getTable(TableName.valueOf(tableName))
        val get = new Get(Bytes.toBytes(rowKey))
        get.addColumn(Bytes.toBytes(cf), Bytes.toBytes(cn))
        val result = table.get(get)
        table.close()
        Bytes.toString(result.value())
    }
}

package org.gumiho.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.stereotype.Service;

@Service
public class HbaseServiceImpl implements HbaseService {

    @Autowired
    private HbaseTemplate hbaseTemplate;
    //TODO setName
    private String table = "cf_foo:table_bar";


}

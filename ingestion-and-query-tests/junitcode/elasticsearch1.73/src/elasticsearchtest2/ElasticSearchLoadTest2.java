package elasticsearchtest2;

import static org.junit.Assert.*;

import org.junit.*;

import java.util.*;
import java.io.*;

import org.elasticsearch.action.bulk.*;
import org.elasticsearch.common.transport.*;
import org.elasticsearch.client.transport.*;
import org.elasticsearch.common.settings.*;
import org.elasticsearch.common.xcontent.*;

public class ElasticSearchLoadTest2 {
  
  private String [] names={"checkout","order","search","payment"};
  private String [] messages={"Incoming request from code","incoming operation succeeded with code","Operation completed time","transaction performed"};
  private String [] severity={"info","warning","transaction","verbose"};
  private String [] apps={"4D24BD62-20BF-4D74-B6DC-31313ABADB82","5D24BD62-20BF-4D74-B6DC-31313ABADB82","6D24BD62-20BF-4D74-B6DC-31313ABADB82","7D24BD62-20BF-4D74-B6DC-31313ABADB82"};

  private String hostname = "";
  private String indexstr = "";
  private String typestr = "";
  private int port = 0;
  private int node=0;
  private int itemsPerInsert = 0;
  private String clustername = "";
  private static Random rand=new Random();
  

  @Before
  public void setUp() throws Exception {

  }
  
  public ElasticSearchLoadTest2(String paras) {
    /* Paras is a string containing a set of comma separated values for:
     
      hostname
      indexstr
      typestr
      port
      clustername
      node
      itemsPerInsert
    */
    
    // Note: No checking/validation is performed
    
    String delims = "[ ]*,[ ]*"; // comma surrounded by zero or more spaces
    String[] items = paras.split(delims);
    
    
    
    hostname = items[0];
    indexstr = items[1];
    typestr = items[2];
    port = Integer.parseInt(items[3]);
    clustername = items[4];
    node=Integer.parseInt(items[5]);
    
    if (node!=0)
      port = port+(rand.nextInt(node)+1);
    
    itemsPerInsert = Integer.parseInt(items[6]);
    if (itemsPerInsert == 0)
      itemsPerInsert = 1000;
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void BulkBigInsertTest() throws IOException {
    
    Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", clustername).build();
    TransportClient client;
    client = new TransportClient(settings);
    
    try {
      
          client.addTransportAddress(new InetSocketTransportAddress(hostname, port));   
          BulkRequestBuilder bulkRequest = client.prepareBulk();
          Random random = new Random();
          char[] exmarks = new char[12000];
          Arrays.fill(exmarks, 'x');
          String dataString = new String(exmarks);
          
          for(int i=1; i < itemsPerInsert; i++){
            random.nextInt(10);
            int host=random.nextInt(20);
       
            bulkRequest.add(client.prepareIndex(indexstr, typestr).setSource(XContentFactory.jsonBuilder()
              .startObject()
                  .field("@timestamp", new Date())
                  .field("name", names[random.nextInt(names.length)])
                  .field("message", messages[random.nextInt(messages.length)])
                  .field("severityCode", random.nextInt(10))
                  .field("severity", severity[random.nextInt(severity.length)])
                  .field("hostname", "Hostname"+host)
                  .field("hostip", "10.1.0."+host)
                  .field("pid",random.nextInt(10))
                  .field("tid",random.nextInt(10))
                  .field("appId", apps[random.nextInt(apps.length)])
                  .field("appName", "application"+host)
                  .field("appVersion", random.nextInt(5))
                  .field("type", random.nextInt(6))
                  .field("subtype", random.nextInt(6))
                  .field("correlationId", UUID.randomUUID().toString())
                  .field("os", "linux")
                  .field("osVersion", "14.1.5")
                  .field("parameters", "{ke:value,key:value}")
                  .field("data1",dataString)
                  .field("data2",dataString)
              .endObject()
              )
          );
        }
        BulkResponse bulkResponse = bulkRequest.execute().actionGet();
        assertFalse(bulkResponse.hasFailures());
      }
        finally {
            client.close();
        }
    }
  
  @Test
  public void BulkDataInsertTest() throws IOException {
    
    Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", clustername).build();
    TransportClient client;
    client = new TransportClient(settings);
    
    try {        
          client.addTransportAddress(new InetSocketTransportAddress(hostname, port));   
          BulkRequestBuilder bulkRequest = client.prepareBulk();
          //Random random = new Random();
       
          for(int i=1; i< itemsPerInsert; i++){
            rand.nextInt(10);
            int host=rand.nextInt(20);
       
            bulkRequest.add(client.prepareIndex(indexstr, typestr).setSource(XContentFactory.jsonBuilder()
              .startObject()
                  .field("@timestamp", new Date())
                  .field("name", names[rand.nextInt(names.length)])
                  .field("message", messages[rand.nextInt(messages.length)])
                  .field("severityCode", rand.nextInt(10))
                  .field("severity", severity[rand.nextInt(severity.length)])
                  .field("hostname", "Hostname"+host)
                  .field("hostip", "10.1.0."+host)
                  .field("pid",rand.nextInt(10))
                  .field("tid",rand.nextInt(10))
                  .field("appId", apps[rand.nextInt(apps.length)])
                  .field("appName", "application"+host)
                  .field("appVersion", rand.nextInt(5))
                  .field("type", rand.nextInt(6))
                  .field("subtype", rand.nextInt(6))
                  .field("correlationId", UUID.randomUUID().toString())
                  .field("os", "linux")
                  .field("osVersion", "14.1.5")
                  .field("parameters", "{ke:value,key:value}")
              .endObject()
              )
          );
        }
        BulkResponse bulkResponse = bulkRequest.execute().actionGet();
        assertFalse(bulkResponse.hasFailures());
      }
        finally {
            client.close();
        }
    }  
}

package elasticsearchtest3;

import static org.junit.Assert.*;

import org.junit.*;

import java.util.*;
import java.io.*;
import java.net.*;

import org.elasticsearch.action.bulk.*;
import org.elasticsearch.common.transport.*;
import org.elasticsearch.client.transport.*;
import org.elasticsearch.common.settings.*;
import org.elasticsearch.common.xcontent.*;

public class ElasticSearchLoadTest3 {
  
  private String [] names={"checkout","order","search","payment"};
  private String [] messages={"Incoming request from code","incoming operation succeeded with code","Operation completed time","transaction performed"};
  private String [] severity={"info","warning","transaction","verbose"};
  private String [] apps={"4D24BD62-20BF-4D74-B6DC-31313ABADB82","5D24BD62-20BF-4D74-B6DC-31313ABADB82","6D24BD62-20BF-4D74-B6DC-31313ABADB82","7D24BD62-20BF-4D74-B6DC-31313ABADB82"};
  private String countryList="BS,BH,BD,BB,BY,BE,BZ,BJ,BM,BT,BO,BQ,BA,BW,BV,BR,IO,BN,BG,BF,BI,KH,CM,CA,CV,KY,CF,TD,CL,CN,CX,CC,CO,KM,CG,CD,CK,CR,HR,CU,CW,CY,CZ,CI,DK,DJ,DM,DO,EC,EG,SV,GQ,ER,EE,ET,FK,FO,FJ,FI,FR,GF,PF,TF,GA,GM,GE,DE,GH,GI,GR,GL,GD,GP,GU,GT,GG,GN,GW,GY,HT,HM,VA,HN,HK,HU,IS,IN,ID,IR,IQ,IE,IM,IL,IT,JM,JP,JE,JO,KZ,KE,KI,KP,KR,KW,KG,LA,LV,LB,LS,LR,LY,LI,LT,LU,MO,MK,MG,MW,MY,MV,ML,MT,MH,MQ,MR,MU,YT,MX,FM,MD,MC,MN,ME,MS,MA,MZ,MM,NA,NR,NP,NL,NC,NZ,NI,NE,NG,NU,NF,MP,NO,OM,PK,PW,PS,PA,PG,PY,PE,PH,PN,PL,PT,PR,QA,RO,RU,RW,RE,BL,SH,KN,LC,MF,PM,VC,WS,SM,ST,SA,SN,RS,SC,SL,SG,SX,SK,SI,SB,SO,ZA,GS,SS,ES,LK,SD,SR,SJ,SZ,SE,CH,SY,TW,TJ,TZ,TH,TL,TG,TK,TO,TT,TN,TR,TM,TC,TV,UG,UA,AE,GB,US,UM,UY,UZ,VU,VE,VN,VG,VI,WF,EH,YE,ZM,ZW,AX";
  private String orgList="Organization01,Organization02,Organization03,Organization04,Organization05,Organization06,Organization07,Organization08,Organization09,Organization10,Organization11,Organization12,Organization13,Organization14,Organization15,Organization16,Organization17,Organization18,Organization19,Organization20,Organization21,Organization22,Organization23,Organization24,Organization25,Organization26,Organization27,Organization28,Organization29,Organization30,Organization31,Organization32,Organization33,Organization34,Organization35,Organization36,Organization37,Organization38,Organization39,Organization40,Organization41,Organization42,Organization43,Organization44,Organization45,Organization46,Organization47,Organization48,Organization49,Organization50,Organization51,Organization52,Organization53,Organization54,Organization55,Organization56,Organization57,Organization58,Organization59,Organization60,Organization61,Organization62,Organization63,Organization64,Organization65,Organization66,Organization67,Organization68,Organization69,Organization70,Organization71,Organization72,Organization73,Organization74,Organization75,Organization76,Organization77,Organization78,Organization79,Organization80,Organization81,Organization82,Organization83,Organization84,Organization85,Organization86,Organization87,Organization88,Organization89,Organization90,Organization91,Organization92,Organization93,Organization94,Organization95,Organization96,Organization97,Organization98,Organization99,Organization100,Organization101,Organization102,Organization103,Organization104,Organization105,Organization106,Organization107,Organization108,Organization109,Organization110,Organization111,Organization112,Organization113,Organization114,Organization115,Organization116,Organization117,Organization118,Organization119,Organization120,Organization121,Organization122,Organization123,Organization124,Organization125,Organization126,Organization127,Organization128,Organization129,Organization130,Organization131,Organization132,Organization133,Organization134,Organization135,Organization136,Organization137,Organization138,Organization139,Organization140,Organization141,Organization142,Organization143,Organization144,Organization145,Organization146,Organization147,Organization148,Organization149,Organization150,Organization151,Organization152,Organization153,Organization154,Organization155,Organization156,Organization157,Organization158,Organization159,Organization160,Organization161,Organization162,Organization163,Organization164,Organization165,Organization166,Organization167,Organization168,Organization169,Organization170,Organization171,Organization172,Organization173,Organization174,Organization175,Organization176,Organization177,Organization178,Organization179,Organization180,Organization181,Organization182,Organization183,Organization184,Organization185,Organization186,Organization187,Organization188,Organization189,Organization190,Organization191,Organization192,Organization193,Organization194,Organization195,Organization196,Organization197,Organization198,Organization199,Organization200";
  private String [] countryCodes;
  private String [] organizations;
  
  private String[] method={"POST","GET","PUT"};
  private String hostname = "";
  private String indexstr = "";
  private String typestr = "";
  private int port = 0;
  private int node = 0;
  private String clustername = "";
  private static Random rand=new Random();
  private static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  private int [] percent={50,85,100};
  private int [] indices={3,23,200};

  
  // constructor
  
  public ElasticSearchLoadTest3(String paras) {
    /* Paras is a string containing a set of comma separated values for:
     
       hostname
       indexstr
       typestr
       port
       clustername
       node
    */
    
    // Note: No checking/validation is performed
    
    String delims = "[ ]*,[ ]*"; // comma surrounded by zero or more spaces
    String[] items = paras.split(delims);
    countryCodes = countryList.split(delims);
    organizations= orgList.split(delims);
    
    hostname = items[0];
    indexstr = items[1];
    typestr = items[2];
    port = Integer.parseInt(items[3]);
    clustername = items[4];
    node=Integer.parseInt(items[5]);
    if (node!=0) {
      port = port+(rand.nextInt(node)+1);
    }
  }
  
  @Before
  public void setUp() throws Exception {
  }
  
  private  int getDistribution(int min,int max) {
    //this will return the value from min and max 
    //which is a percentage distributin Min=0 max=100
    return rand.nextInt((max - min) + 1) + min;
     
  }
  
  private  String getRandomString(int len, String request){
    
      StringBuilder sb = new StringBuilder( len );
      sb.append(request);
      for( int i = 0; i < len; i++ ) 
        sb.append( AB.charAt( rand.nextInt(AB.length()) ) );
      return sb.toString();    
  }
  
   private String getValue(int[] percent,int[]indext,String[] valueStr) {
        
        String field="";
        int distribution = getDistribution(0,100);
          
        for (int i = 0; i < percent.length; i++)
        {
          if (distribution<=percent[i])
          {
            if(i==0)
              field=valueStr[getDistribution(0,indext[i])];              
            
            else
              field=valueStr[getDistribution(indext[i-1],indext[i])];  
            
            break;
          }
        }
        return field; 
   }
  

  @After
  public void tearDown() throws Exception {
  }
  
  @Test
  public void DataInsertDcuTest() throws IOException {
    
    Settings settings = Settings.settingsBuilder()
            .put("cluster.name", clustername).build();
    
    
    TransportClient client = TransportClient.builder().settings(settings).build();
        
    try {
      client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(hostname), port));
      BulkRequestBuilder bulkRequest = client.prepareBulk();     

      for(int i=1; i<1000; i++){  
        indices[2]=(organizations.length-1);
        String org=getValue(percent,indices,organizations);
        indices[2]=(countryCodes.length-1);         
        String country=getValue(percent,indices,countryCodes);
        String randomip;
        String targetip;
        if( getDistribution(0,100) <= 20)
          randomip="192.1.0."+rand.nextInt(200);
        else
          randomip=rand.nextInt(256) + "." + rand.nextInt(256) + "." + rand.nextInt(256) + "." + rand.nextInt(256);
           
        targetip=rand.nextInt(256) + "." + rand.nextInt(10) + ".100.100";
        String latlong= rand.nextInt(10)+"."+rand.nextInt(10000)+","+rand.nextInt(100)+"."+rand.nextInt(9000);
       
        bulkRequest.add(client.prepareIndex(indexstr, typestr).setId(UUID.randomUUID().toString().substring(1,18)).setSource(XContentFactory.jsonBuilder()
          .startObject()                                                          
            .field("Organization",org)
            .field("CustomField1", "")
            .field("CustomField2", "")
            .field("CustomField3", "")
            .field("CustomField4", "")
            .field("CustomField5", "")
            .field("DateTimeReceivedUtc", new Date())
            .field("Host", "")
            .field("HttpMethod", method[rand.nextInt(method.length)])
            .field("HttpReferrer","")
            .field("HttpRequest",getRandomString(rand.nextInt(200),"request"))
            .field("HttpUserAgent", "")
            .field("HttpVersion", "")
            .field("OrganizationName", org)
            .field("SourceIp", randomip)
            .field("SourceIpAreaCode", 0)
            .field("SourceIpAsnNr", "AS#####")
            .field(" SourceIpBase10", 500)                
            .field("SourceIpCity", "city"+rand.nextInt(10)+"of"+country)
            .field("SourceIpCountryCode", country)
            .field("SourceIpLatitude", rand.nextDouble())
            .field("SourceIpLongitude", rand.nextDouble())
            .field("SourceIpMetroCode", 0)
            .field("SourceIpPostalCode", "")
            .field("SourceLatLong", latlong)
            .field("SourcePort", getDistribution(4000,4500))
            .field("TargetIp", targetip)
            .field("SourcedFromt", "MonitoringCollector")
            .field("TargetPort", getDistribution(9000,9500))
            .field("Rating", "rating"+rand.nextInt(20))
            .field("UseHumanReadableDateTimes", false)
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
  public void BulkDataIdInsertTest() throws IOException {
    
    Settings settings = Settings.settingsBuilder()
            .put("cluster.name", clustername).build();
    TransportClient client = TransportClient.builder().settings(settings).build();                       
    
    try {
      
          client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(hostname), port));
          BulkRequestBuilder bulkRequest = client.prepareBulk();       
          
          for(int i=1; i<1000; i++){
            rand.nextInt(10);
            int host=rand.nextInt(20);
       
            bulkRequest.add(client.prepareIndex(indexstr, typestr).setId(UUID.randomUUID().toString()).setSource(XContentFactory.jsonBuilder()
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
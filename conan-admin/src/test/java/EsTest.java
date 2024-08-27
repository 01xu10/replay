import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class EsTest {

    public static void main(String[] args) {
//        QueryBuilder shouldQuery = QueryBuilders.boolQuery()
//                .should(QueryBuilders.termQuery("server_name.keyword", "192.168.200.128"));
//        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery()
//                .must(shouldQuery);
//        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//        log.info(queryBuilder.toString());
//        sourceBuilder.query(queryBuilder);
//        sourceBuilder.sort("@timestamp", SortOrder.DESC);
//        sourceBuilder.size(10);
//        sourceBuilder.trackTotalHits(true);
//        Scroll scroll = new Scroll(TimeValue.timeValueMinutes(10L));
//        SearchRequest searchRequest = new SearchRequest();
//        searchRequest.source(sourceBuilder).scroll(scroll).indices();
//        //根据域名获取对应es配置信息
//        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(RestClient.
//                builder(new HttpHost("192.168.200.128",9200)));
//        SearchResponse searchResponse = null;
//        long totalElements = 0;
//        try {
//            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.error("日志查询失败" + e);
//        }
//        String scrollId = searchResponse.getScrollId();
//
//        SearchHits searchHits = searchResponse.getHits();
//        //获取记录数
//        totalElements = searchHits.getTotalHits().value;
//
//        // value = searchHits.getTotalHits().value;
//        System.out.println("总记录数："+totalElements);
//
//        int total = (int) totalElements;
//        log.info("查询到总流量" + totalElements + "条");
//        int length = searchResponse.getHits().getHits().length;
//        log.info("当前页" + length + "");
        addData();
       // test2();
    }

    @SneakyThrows
    private static  void addData(){
            // Test insert data
            RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("82.156.80.137", 9200, "http")));//初始
            BulkRequest bulkRequest =new BulkRequest("mylogs");
            for (int i = 0; i <1000 ; i++) {
//            NginxRequst nginxRequst = new NginxRequst();
//            nginxRequst.setId(""+System.nanoTime());
//            nginxRequst.setRequest("GET /api/login");
//            nginxRequst.setServerName("192.168.200.128");
//            nginxRequst.setRequestMethod("Get");
//            nginxRequst.setRequestBody("");

                Map map =new HashMap<String,Object>();
                String id = ""+System.nanoTime();
                map.put("id",id);
                map.put("request","GET /pinter/com/getSku?id=1");
                map.put("server_name","82.156.74.26:9088");
                map.put("request_method","GET");
                map.put("@timestamp",new Date());
                map.put("request_body","");

                IndexRequest request = new IndexRequest().id(""+System.nanoTime())
                        .source(map);
                bulkRequest.add(request);
            }
            client.bulk(bulkRequest, RequestOptions.DEFAULT);

        System.out.println("0k------------");

    }


    private static void test2(){
        QueryBuilder shouldQuery = QueryBuilders.boolQuery()
                .should(QueryBuilders.termQuery("serverName.keyword", "192.168.200.128"));
//        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery()
//                .must(QueryBuilders.wildcardQuery(url+ ".keyword", method + " " + requestQuery.getApi() + "*"))
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.wildcardQuery("request.keyword", "GET" + " " + "/api/login" + "*"))
                .must(shouldQuery);

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        sourceBuilder.query(queryBuilder);
        //sourceBuilder.sort("@timestamp", SortOrder.ASC);
        sourceBuilder.size(500);
        Scroll scroll = new Scroll(TimeValue.timeValueMinutes(10L));
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.source(sourceBuilder).scroll(scroll).indices();
        //根据域名获取对应es配置信息
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(RestClient.
                builder(new HttpHost("192.168.200.128",9200)));
        SearchResponse searchResponse = null;
        try {
            log.info(queryBuilder.toString());
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.error("日志查询失败" + e);
        }
        String scrollId = searchResponse.getScrollId();
        log.info("scroll_id=" + scrollId);
        long totalElements = searchResponse.getHits().getTotalHits().value;
        int total = (int) totalElements;
        log.info("查询到总流量" + totalElements + "条");
    }
}

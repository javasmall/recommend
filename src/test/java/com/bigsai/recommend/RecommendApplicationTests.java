package com.bigsai.recommend;

import com.bigsai.recommend.dao.newsMapper;
import com.bigsai.recommend.dao.userMapper;
import com.bigsai.recommend.pojo.news;
import com.bigsai.recommend.service.NewsService;
import com.bigsai.recommend.service.UserService;
import com.bigsai.recommend.tf.TFIDF;
import org.ansj.app.keyword.KeyWordComputer;
import org.ansj.app.keyword.Keyword;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;

@SpringBootTest
class RecommendApplicationTests {

    static Logger log = LoggerFactory.getLogger(RecommendApplicationTests.class);
    @Autowired(required = false)
    public TFIDF tfidf;

    @Test
    void contextLoads() {
        String str="有俄罗斯国会议员，9号在社交网站推特表示，美国中情局前雇员斯诺登，已经接受委内瑞拉的庇护，" +
                "不过推文在发布几分钟后随即删除。俄罗斯当局拒绝发表评论，而一直协助斯诺登的维基解密否认他将投靠委内瑞拉。　" +
                "　俄罗斯国会国际事务委员会主席普什科夫，在个人推特率先披露斯诺登已接受委内瑞拉的庇护建议，令外界以为斯诺登的动向终于有新进展。　　" +
                "不过推文在几分钟内旋即被删除，普什科夫澄清他是看到俄罗斯国营电视台的新闻才这样说，而电视台已经作出否认，称普什科夫是误解了新闻内容。" +
                "　　委内瑞拉驻莫斯科大使馆、俄罗斯总统府发言人、以及外交部都拒绝发表评论。而维基解密就否认斯诺登已正式接受委内瑞拉的庇护，" +
                "说会在适当时间公布有关决定。　　斯诺登相信目前还在莫斯科谢列梅捷沃机场，已滞留两个多星期。他早前向约20个国家提交庇护申请，委内瑞拉、" +
                "尼加拉瓜和玻利维亚，先后表示答应，不过斯诺登还没作出决定。　　而另一场外交风波，玻利维亚总统莫拉莱斯的专机上星期被欧洲多国以怀疑斯诺登在机上为由拒绝过境事件，" +
                "涉事国家之一的西班牙突然转口风，外长马加略]号表示愿意就任何误解致歉，但强调当时当局没有关闭领空或不许专机降落。人类善待野生动物的理念和实践在我国有着漫长的历史。到了宋代又有了新的表现和探索，这较好地体现在诗和词这两种当时最为流行的艺术形式之中。在宋代诗词中，一方面有围绕人类善待野生动物的方式方法进行的细致描写，另一方面还贯穿着一些具有强烈人文意识的独到理念。\n" +
              "";

        Map<String,Double>map=tfidf.getTf(str);
        for(String key:map.keySet())
        {
            System.out.println(key+" "+map.get(key));
        }
    }
    @Autowired(required = false)
    private newsMapper newsMapper;
    @Test
    public  void test2()
    {
        List<news>list=newsMapper.getnews();
        for(news new1:list)
        {
            System.out.println(new1.getId()+" "+new1.getBiref()+" "+new1.getContent());
        }
    }

    @Autowired(required = false)
    private com.bigsai.recommend.tf.TopkWord topKword;
    @Test
    public void test3() throws Exception {
        String content="被誉为“病毒猎手”的美国传染病学专家伊恩·利普金（Ian Lipkin）教授日前在接受采访时表示，新冠病毒可能已经在人类中传播了几个月甚至数年时间。\n" +
                "　　据美国有线电视新闻网（CNN）4月4日报道，美国哥伦比亚大学公共卫生学院感染与免疫中心主任利普金表示，新冠病毒可能不仅是过去几个月中来自蝙蝠，而是有可能数月甚至数年前就进入了人类体内，最终转变为“人传人”的致命病毒。\n" +
                "　　“我认为它可能在人类中传播了一段时间。”利普金说：“多久？ 我们可能永远不会完全重建这一过程……它可能已经流传了几个月甚至几年时间。”\n" +
                "　　科学杂志《自然医学》近日（3月17日）也发布最新研究称，新冠病毒为自然产物，不可能是在实验室中构建的，且新冠病毒之前或以相对弱化的形式在人群中传播，甚至可能已存在多年。病毒在发生突变后，才触发大流行。\n" +
                "　　3月24日，利普金在接受美国福克斯商业频道采访时确认，自己新冠病毒检测呈阳性。今年1月新冠疫情暴发之初，利普金曾以个人名义到中国考察疫情防控情况。";
        Map<String,Double>TF=tfidf.getTf(content);
        List<Map.Entry<String,Double>>list=topKword.getTopk(content);
        for(Map.Entry<String,Double> m:list)
        {
           System.out.println(m.getKey()+" "+m.getValue());
        }

    }
    @Autowired(required = false)
    com.bigsai.recommend.service.TrainService train;
    @Autowired(required = false)
    private NewsService newsService;

    @Autowired(required = false)
    RedisTemplate redisTemplate;

    @Test
    public void test4() throws Exception {
        Map<String ,Integer>allWordTimes=(Map<String, Integer>) redisTemplate.opsForValue().get("idf");
        log.info(allWordTimes.toString());
//        train.trainContent();

//        newsTopkWeight newstopk=new newsTopkWeight("55");
//
//        newstopk.setNewsId("55");
//
//        Map<String,Double>map=new HashMap<>();
//        map.put("n",2.3);
//        map.put("85",5.2);
//        List<Map.Entry<String,Double>>list=new ArrayList<>();
//        for(Map.Entry<String,Double>m:map.entrySet())
//        {
//            list.add(m);
//        }
//        newstopk.setContentTfidf(list);
//        System.out.println(newstopk.toString());
//
//        String s=JSON.toJSONString(list);
//        System.out.println(s);
//        List<Map.Entry<String,Double>>list2= JSON.parseObject(s,new TypeReference<List<Map.Entry<String,Double>>>(){});
//
//        newstopk.setTopk("555");
//       System.out.println(list2.get(0).getKey());
//       newsService.insertTopk(newstopk);


    }
    @Test
    public void test5() throws Exception {
        String content="被誉为“病毒猎手”的美国传染病学专家伊恩·利普金（Ian Lipkin）教授日前在接受采访时表示，新冠病毒可能已经在人类中传播了几个月甚至数年时间。\n" +
                "　　据美国有线电视新闻网（CNN）4月4日报道，美国哥伦比亚大学公共卫生学院感染与免疫中心主任利普金表示，新冠病毒可能不仅是过去几个月中来自蝙蝠，而是有可能数月甚至数年前就进入了人类体内，最终转变为“人传人”的致命病毒。\n" +
                "　　“我认为它可能在人类中传播了一段时间。”利普金说：“多久？ 我们可能永远不会完全重建这一过程……它可能已经流传了几个月甚至几年时间。”\n" +
                "　　科学杂志《自然医学》近日（3月17日）也发布最新研究称，新冠病毒为自然产物，不可能是在实验室中构建的，且新冠病毒之前或以相对弱化的形式在人群中传播，甚至可能已存在多年。病毒在发生突变后，才触发大流行。\n" +
                "　　3月24日，利普金在接受美国福克斯商业频道采访时确认，自己新冠病毒检测呈阳性。今年1月新冠疫情暴发之初，利普金曾以个人名义到中国考察疫情防控情况。";

        newsService.recommendBynewsId("ARTI05wNQi5kNrYZPeWRX0xo200424");
        //train.trainContent();
        System.out.println(Math.log(Math.E)+" "+Math.log(5)+" "+Math.log(10)+" "+Math.log(20)+" "+Math.log(100)+" "+Math.log(1000));
    }

    @Autowired(required = false)
    com.bigsai.recommend.service.dailyUpdateService dailyUpdateService;
    @Test
    public  void  test6()
    {
        dailyUpdateService.userPreferDecline();
    }
    @Autowired(required = false)
    userMapper userMapper;
    @Autowired(required =false)
    UserService userService;
    @Test
    public  void test7()
    {
        userService.readAriticle("ARTILiq5zEcUCxtJfMpwqpIt200422","bigsai");
    }


    @Test
    public  void test8()
    {
        dailyUpdateService.userPreferDecline();
    }

    @Test
    public void test9()
    {
        newsService.recommendByusername("bigsai");
    }
}

package com.bigsai.recommend;

import com.bigsai.recommend.dao.newsMapper;
import com.bigsai.recommend.dao.userMapper;
import com.bigsai.recommend.pojo.news;
import com.bigsai.recommend.pojo.users;
import com.bigsai.recommend.service.dailyUpdateService;
import com.bigsai.recommend.service.newsService;
import com.bigsai.recommend.service.userService;
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
                "一\n" +
                "　　善待野生动物的前提是准确认识和深刻理解野生动物的天然禀性和各种“不得不然”的行为，也就是野生动物在自然规律作用下展现的各种特点和功能。\n" +
                "　　首先，客观地理解野生动物的本能需求和“向善”可能性。梅尧臣《猛虎行》写老虎“猛气吞赤豹，雄威蹑封狼”“当途食人肉，所获乃堂堂”，而且代老虎言：“食人既我分，安得为不祥？”“而欲我无杀，奈何饥馁肠？”这是肯定猛虎食用其他动物（包括人）是来自自然的本性和基本的生理需要，是上天赋予它的“权力”和无可改移的生物本性。这一理念在五代僧齐己《猛虎行》中早有论述：“横行不怕日月明，皇天产尔为生狞。”黄庭坚《观道二篇》言：“圣人用仁心，恻伤路傍儿。虎狼舐吻血，自哺胃与肌。同在天地闲，六凿相识知。父母临万物，大道甚坦夷。百年修不善，一日许知非。虎狼有悛心，还与圣人齐。”这里一方面肯定虎狼和圣人一样有自己的本性，另一方面又肯定了虎狼有“改过迁善”的可能性。应该说，这里既包含着道家“齐万物”的思想意味，又承接儒家仁爱的思想主旨。\n" +
                "　　其次，肯定野生动物的现实价值和审美价值。野生动物对于人类社会的价值可以辩证地理解，既表现出明显的负面价值，也表现出各种各样的正面功能。黎廷瑞《听山中谈虎赋二章》讲到当时有个地方“千百群”的野猪祸害庄稼，以至于百姓“终岁举室空辛勤”，但“近日南山老虎至，野彘畏之俱远避。遂令一枕得安眠，犬豕时时亦遭噬。呜呼，犬豕所噬能几何，野彘不去为害多”。也就是说，老虎来了之后虽然经常吃百姓的猪狗，但把成群结队的野猪赶跑了，这样庄稼保住了，百姓认为这是合算的。这里诗人肯定了猛虎“有功有过”“功大于过”的现实价值。部分野生动物还有较大的审美价值，因而得到诗人词人的欣赏和喜爱。范仲淹非常喜欢鹤的仪态和声音，写有“八变奇姿已过人”“独爱九皋嘹唳好，声声天地为之清”的称赞诗句。欧阳修非常喜欢鸟类，其《啼鸟》言：“鸟言我岂解尔意，绵蛮但爱声可听。”“黄鹂颜色已可爱，舌端哑咤如娇婴。”野禽给诗人带来的愉悦跃然纸上。现实价值和审美价值是野生动物得到善待的重要原因。\n" +
                "　　最后，肯定野生动物有类似人类的知觉和情感。两宋人士拥有细致精微、丰富多彩的感情世界，他们从心理的角度来理解动物，通过诗词细致地描写了野生动物的亲子之情、两性之情，等等。南宋林同写有《禽兽昆虫之孝十首》，展现的是动物的亲子之情，其中大部分是野生动物。李石《蜂蚁》写到一个小蜂被十个蚂蚁抬走：“蜂母正凭怒，有虿无敢作。相视母子心，如被蛇豕虐。蚁行蜂母随，众力岂易拨。”这是作者亲眼观察到的动物活动细节，生动形象地展现了虫类的母子之情。南宋史达祖《双双燕·咏燕》说：“差池欲住，试入旧巢相并。还相雕梁藻井。又软语、商量不定。”吴文英《双双燕·小桃谢后》说：“共斜入、红楼深处。相将占得雕梁，似约韶光留住。”这二位婉约派词人显然是把双飞燕子描写成“腻在一起”的夫妻，这也是中国古典诗歌的有趣传统。肯定野生动物的心理特性和类于、通于人类的情感，这为善待野生动物提供了情感前提。\n" +
                "二\n" +
                "　　宋代诗词细致描写了对待野生动物的各种场景和操作环节。其中以和善仁爱的方式对待野生动物的活动也有不少记载，由于场合和条件不同，我们可以从三个方面把握。\n" +
                "　　一是在日常生活之中尽量以善心慈念对待野生动物，这反映了人的文明素养和人道精神。王安石的《放鱼》说：“捉鱼浅水中，投置最深处。当暑脱煎熬，翛然泳而去。岂无良庖者，可使供七箸。物我皆畏苦，舍之宁啖茹。”这是荆公所记自己救鱼食素的一次生活经历。他还有一首《同王浚贤良赋龟得升字》，记载了他费尽心力处理一只数百年老龟的办法：“浅樊荒圃不可保，守视且寄钟山僧。”苏东坡《次韵定慧钦长老见寄八首》说：“钩帘归乳燕，穴纸出痴蝇。为鼠常留饭，怜蛾不点灯。”这都是日常生活中人们经常能碰到的事，举手之劳可以为动物放一条生路。虽然东坡的说法有一定宗教背景，但对普通人也有启示意义。\n" +
                "　　二是尽量人道地对待捕获的野生动物。人类在和野生动物的“交往”中肯定会捉取大量野生动物。除掉大部分被杀掉之外，人类还有较为“友善”地对待它们的方式方法。宋代有大量的放生活动，这在诗词中有许多描写。欧阳修《驯鹿》讲到诗人自己想放掉一只被网捉住的鹿，“南山蔼蔼动春阳，吾欲纵尔山之傍”“饮泉啮草当远去，山后山前射生户”，他不仅希望这头驯鹿回到适宜自己生存的山野之中，而且规劝它避开猎户。陈宓《放鹧鸪》说自己不接受“以彼刳肠苦，为吾悦口甘”，所以“放汝飞翔去，腾云更宿岚”。这里写的是作者宁可“蔬餐”也要放鸟飞回自然界中。不过，当时诗人们关于放生产生了意见分歧。杨备《长命洲》写道：“狐狸口腹应潜饱，就死多于日放生。”也就是说，放生的动物大多被狐狸吃掉了。马之纯《长命洲》写道：“不杀自然能不放，却将实祸博虚声。”他认为放生实际上是一种导致动物灾祸的方式，但放生者却得到了好的名声。这里杨、马二位是从保护野生动物的角度反对放生这种形式。还有一种方式是把捕获的野生动物饲养起来。苏洵《欧阳永叔白兔》讲到欧阳修得到一只“白兔不忍杀，叹息爱其老”。赵抃饲养了一只鹤和一只白龟，后来都放到自然界中。他的诗写道：“马寻旧路如归去，龟放长淮不再来。”\n" +
                "　　三是在渔猎活动中尽量给野生动物留一线生路。渔猎是人类十分残酷的一种实践活动，在传统社会中它又具有一定的必然性和合理性。在这种活动中更有保留“一念之善”的必要性。范仲淹写有《观猎》一诗，讲到对野生动物“翦棘争探穴，摧林竞覆巢”的残杀，但同时也强调“惟开三面者，盛德播弦匏”。这里他借助商汤“网开三面”的故事强调要给野生动物留下一线生机，实际上是在保留动物的族类。苏辙《和子瞻司竹监烧苇园因猎园下》讲道：“吾兄善射久无敌，是日敛手称不能。凭鞍纵马聊自适，酒后醉语谁能应？”这里写到苏轼在这种烧猎中不愿射野兽，应该和他对这种猎杀野兽的方式不满有关。秦观《和裴仲谟放兔行》、郑伯英《放龟》写了诗人从猎人、渔夫手中买下兔、龟放入山林、水泽的事。从先秦时代起，我国就有渔猎而不斩尽杀绝的传统，在宋代这一传统仍然得到较多表现。\n" +
                "　　可以说，宋代诗词在很大程度上反映了那个时代人类和野生动物关系的真实状态。从思想资源来看，这些善待动物的理念主要来自儒家的仁爱思想和佛教的慈悲观念，在快速发展的当下，我们可以从宋人的思想和实践中得到一些有益的启示和借鉴。\n" +
                "　　（作者：刘东超，系中共中央党校〔国家行政学院〕文史部教授）";

        Map<String,Double>map=tfidf.getTf(str);
        for(String key:map.keySet())
        {
            System.out.println(key+" "+map.get(key));
        }
//        System.out.println("BASE");
//        System.out.println(BaseAnalysis.parse(str));
//        System.out.println("TO");
//        System.out.println(ToAnalysis.parse(str));
//        Result result=ToAnalysis.parse(str);
//        System.out.println(result.toString());
//        log.info(result.toString());
//
//        List<Term>list=result.getTerms();
//        for(Term t:list)
//        {
//            System.out.print(t+" ");
//        }
//        System.out.println("关键词提取");
        KeyWordComputer kwc = new KeyWordComputer();
        Collection<Keyword> result2 = kwc.computeArticleTfidf(str);
        for(Keyword key:result2)
        {
            System.out.println(key.getFreq()+" "+key.getScore()+" "+key.getName()+" ");
        }
//        log.info(result2.toString());
//        System.out.println("DIC");
//        System.out.println(DicAnalysis.parse(str));
//        System.out.println("INDEX");
//        System.out.println(IndexAnalysis.parse(str));
//        System.out.println("NLP");
//        System.out.println(NlpAnalysis.parse(str));
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
    private newsService newsService;

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
    userService userService;
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

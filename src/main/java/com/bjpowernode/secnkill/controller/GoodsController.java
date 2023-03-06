package com.bjpowernode.secnkill.controller;

import com.bjpowernode.secnkill.pojo.User;
import com.bjpowernode.secnkill.service.GoodsService;
import com.bjpowernode.secnkill.service.UserService;
import com.bjpowernode.secnkill.vo.DetailVo;
import com.bjpowernode.secnkill.vo.GoodsVo;
import com.bjpowernode.secnkill.vo.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.IContext;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PushbackReader;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller

public class GoodsController {
   @Resource
    private RedisTemplate redisTemplate;
   @Resource
   private ThymeleafViewResolver thymeleafViewResolver;


    @Resource
    private UserService userService;
    @Resource
    private GoodsService goodsService;


    @RequestMapping(value = "/goods/tolist",produces = "text/html;charset=utf-8")
    @ResponseBody
    public String test01( Model model,User user, HttpServletRequest request,
                          HttpServletResponse response){
        //页面优化
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String redishtml=(String)valueOperations.get("goods");
        if(! StringUtils.isEmpty(redishtml)){
            return redishtml;
        }
//        if(StringUtils.isEmpty(ticket)){
//            return "login";
//        }
//        User user = userService.getUserCookie(ticket, request, response);
//        if(user == null){
//            return "login";
//        }
        if (user == null){
            return  "login";
        }
        List<GoodsVo> goodsVo = goodsService.findGoodsVo();
        System.out.println(goodsVo);
        model.addAttribute("user",user);
        model.addAttribute("goodsList",goodsVo);
        //优化使用页面缓存(采用redis缓存)
            WebContext webContext=new WebContext(request,response,request.getServletContext(),request.getLocale(),
                    model.asMap());
            String thyhtml = thymeleafViewResolver.getTemplateEngine().process("goods", webContext);
            if (! StringUtils.isEmpty(thyhtml)) {
                valueOperations.set("goods", thyhtml,69, TimeUnit.SECONDS);
            }
            return thyhtml;
    }
    @RequestMapping("/goods/detail")
    @ResponseBody
    public RespBean test02(Model model, User user, Integer goodsId
                         , HttpServletRequest request, HttpServletResponse response){
        System.out.println(goodsId);
        GoodsVo goods = goodsService.findGoodsVoById(goodsId);
        //判断秒杀状态
        Date startDate = goods.getStartDate();
        Date endDate = goods.getEndDate();
        Date date = new Date();
        int seckillStatus=0;
        /*seckillStatus=0 秒杀倒计时
          seckillStatus=1 秒杀进行中
          seckillStatus=2 秒杀已结束
         */
        //秒杀倒计时
        int remainTime=0;
        if(date.before(startDate)){
             //处理在页面显示的倒计时
             remainTime= (int) ((startDate.getTime()-date.getTime())/1000);
        }else if (date.after(endDate)){
            //秒杀已结束
            seckillStatus=2;
            remainTime=-1;
        }else {
            //秒杀进行中
            seckillStatus=1;
            remainTime=0;

        }
        DetailVo vo=new DetailVo();
        vo.setUser(user);
        vo.setGoodsVo(goods);
        vo.setRemainTime(remainTime);
        vo.setSeckillStatus(seckillStatus);
        return RespBean.success(vo);
    }
}

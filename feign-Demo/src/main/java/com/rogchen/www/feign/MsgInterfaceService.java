package com.rogchen.www.feign;

import com.ylzinfo.cloud.auth.client.feign.AuthFeignConfiguration;
import com.ylzinfo.dto.impl.CommonResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description: 对接消息发布接口
 * @Product: IntelliJ IDEA
 * @Author Rogchen chenhk128@163.com
 * @Created Date: 2018/4/24 13:39
 **/
@FeignClient(value = "integrated-management-rogchen",configuration = AuthFeignConfiguration.class)
public interface MsgInterfaceService {

    /**
     * 通过用户获取个人应用推送消息分页
     * @param userId
     * @param limit
     * @param offset
     */
    @RequestMapping(value = "/ws/getAppSendRecord.sjson",method = RequestMethod.POST)
    CommonResult getAppSendRecord(@RequestParam("userId") String userId, @RequestParam("limit") Integer limit, @RequestParam("offset") Integer offset);

    /**
     *  通过父id获取通道列表（分类）
     * @param parentId
     * @return
     */
    @RequestMapping(value = "/ws/getChannelList.sjson",method = RequestMethod.GET)
    CommonResult getChannelList(@RequestParam("parentId") Long parentId);

    /**
     * 通过通道id获取内容信息-分页
     * @param channelId
     * @param limit
     * @param offset
     */
    @RequestMapping(value = "/ws/getContentList.sjson",method = RequestMethod.GET)
    CommonResult getContentList(@RequestParam("channelId") long channelId,@RequestParam("title") String title, @RequestParam("limit") String limit, @RequestParam("offset") String offset);

    /**
     * 获取详细内容
     * @param contentId
     * @return
     */
    @RequestMapping(value = "/ws/getContentById.sjson",method = RequestMethod.GET)
    CommonResult getContentById(@RequestParam("contentId") long contentId);

    /**
     * 测试内部是否进行鉴权校验
     */
    @RequestMapping(value = "/rog/getFeign",method = RequestMethod.GET)
    CommonResult getFeign();


}

package com.shopping.api.modules.user.service;

import com.shopping.api.modules.user.vo.UserVo;
import org.apache.ibatis.annotations.Param;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * dubbo rest协议访问的路径为：http://localhost:8089/users/findList
 * 请求头设置Content-Type为application/json
 *
 */
@Path("/users")
@Consumes({MediaType.APPLICATION_JSON}) // #2
@Produces({MediaType.APPLICATION_JSON})
public interface UserService {
    @POST
    @Path("save")
    Boolean save(UserVo userVo);

    @GET
    @Path("/findByAccount")
    UserVo findByAccount(@Param("userAccount") String userAccount);

    @GET
    @Path("/findList")
    List<UserVo> findList(String userAccount);
}

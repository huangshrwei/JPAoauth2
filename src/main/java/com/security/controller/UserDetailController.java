package com.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.security.common.api.CommonResult;
import com.security.dao.UserDetailRepository;
import com.security.dto.UserDetailDto;
import com.security.entity.UserDetail;
import com.security.service.UserDetailService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import javax.annotation.security.RolesAllowed;

@Api(tags = "UserDetailController")
@Tag(name = "UserDetailController", description = "使用者基本資料")
@RestController
@RequestMapping(value = "/userDetail")
@Slf4j
public class UserDetailController {

    @Autowired
    private UserDetailService userDetailService;  

    @ApiOperation(value = "取得使用者詳細資料", notes = "列出所有使用者詳細資料")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/listAll")
    @RolesAllowed({"ADMIN"})
    public List<UserDetail> getAll() {
        return userDetailService.listAllUserDetail();
    }  

    @ApiOperation("新增員工資料")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult createProducts(@RequestBody UserDetailDto userDetailDto) {
        CommonResult commonResult;
        int count = userDetailService.createUserDetail(userDetailDto);
        if (count == 1) {
            commonResult = CommonResult.success(userDetailDto);
            log.debug("createUserDetail success:{}", userDetailDto);
        } else {
            commonResult = CommonResult.failed("操作失败");
            log.debug("createUserDetail failed:{}", userDetailDto);
        }
        return commonResult;
    }

    @ApiOperation("更新指定id員工資料")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateUserDetail(@PathVariable("id") Long id, @RequestBody UserDetailDto userDetailDto, BindingResult result) {
        CommonResult commonResult;
        int count = userDetailService.updateUserDetail(id, userDetailDto);
        if (count == 1) {
            commonResult = CommonResult.success(userDetailDto);
            log.debug("updateUserDetail success:{}", userDetailDto);
        } else {
            commonResult = CommonResult.failed("操作失败");
            log.debug("updateUserDetail failed:{}", userDetailDto);
        }
        return commonResult;
    }

    @ApiOperation("删除指定id員工資料")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult deleteProducts(@PathVariable("id") Long id) {
        int count = userDetailService.deleteUserDetail(id);
        if (count == 1) {
            log.debug("deleteUserDetail success :id={}", id);
            return CommonResult.success(null);
        } else {
            log.debug("deleteUserDetail failed :id={}", id);
            return CommonResult.failed("操作失败");
        }
    }

    @ApiOperation("分页查询員工資料列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<UserDetail>> listUserDetail(@RequestParam(value = "pageNum", defaultValue = "1")
                                                        @ApiParam("页码") Integer pageNum,
                                                        @RequestParam(value = "pageSize", defaultValue = "3")
                                                        @ApiParam("每页数量") Integer pageSize) {
        List<UserDetail> userDetailList = userDetailService.listUserDetail(pageNum, pageSize);
        return CommonResult.success(userDetailList);
    }

    @ApiOperation("取得指定id員工資料")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<UserDetail> userDetail(@PathVariable("id") Long id) {
        return CommonResult.success(userDetailService.getUserDetail(id));
    }        
    
}
package com.youlai.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.admin.entity.SysRole;
import com.youlai.admin.service.ISysRoleService;
import com.youlai.common.result.PageResult;
import com.youlai.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Api(tags = "角色接口")
@RestController
@RequestMapping("/roles")
@Slf4j
public class SysRoleController {


    @Autowired
    private ISysRoleService iSysRoleService;

    @ApiOperation(value = "列表分页", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "limit", value = "每页数量", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "username", value = "角色名", paramType = "query", dataType = "String"),
    })
    @GetMapping
    public Result list(Integer page, Integer limit, String name) {
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<SysRole>()
                .like(StrUtil.isNotBlank(name), SysRole::getName, name)
                .orderByDesc(SysRole::getUpdateTime)
                .orderByDesc(SysRole::getCreateTime);

        if (page != null && limit != null) {
            Page<SysRole> result = iSysRoleService.page(new Page<>(page, limit) ,queryWrapper);

            return PageResult.success(result.getRecords(), result.getTotal());
        } else if (limit != null) {
            queryWrapper.last("LIMIT " + limit);
        }
        List<SysRole> list = iSysRoleService.list(queryWrapper);
        return Result.success(list);
    }

    @ApiOperation(value = "角色详情", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "角色id", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        SysRole sysRole = iSysRoleService.getById(id);
        return Result.success(sysRole);
    }

    @ApiOperation(value = "新增角色", httpMethod = "POST")
    @ApiImplicitParam(name = "sysRole", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysRole")
    @PostMapping
    public Result add(@RequestBody SysRole sysRole) {
        boolean status = iSysRoleService.save(sysRole);
        return Result.status(status);
    }

    @ApiOperation(value = "修改角色", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色id", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "sysRole", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysRole")
    })
    @PutMapping(value = "/{id}")
    public Result update(
            @PathVariable Long id,
            @RequestBody SysRole sysRole) {
        sysRole.setUpdateTime(new Date());
        boolean status = iSysRoleService.updateById(sysRole);
        return Result.status(status);
    }

    @ApiOperation(value = "删除角色", httpMethod = "DELETE")
    @ApiImplicitParam(name = "ids[]", value = "id集合", required = true, paramType = "query", allowMultiple = true, dataType = "Long")
    @DeleteMapping
    public Result delete(@RequestParam("ids") List<Long> ids) {
        boolean status = iSysRoleService.removeByIds(ids);
        return Result.status(status);
    }

}

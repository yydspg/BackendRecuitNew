package com.pg.backend.security.domain;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author paul 2024/3/25
 */
@Getter
@Setter
public class SysUser {
    @Schema(description = "系统用户ID")
    private Long sysUserId;

    @Schema(description = "登录用户名")
    private String loginUsername;

    @Schema(description = "真实姓名")
    private String realname;

    @Schema(description = "手机号")
    private String telphone;

    @Schema(description = "性别 0-未知, 1-男, 2-女")
    private Byte sex;

    @Schema(description = "头像地址")
    private String avatarUrl;

    @Schema(description = "员工编号")
    private String userNo;

    @Schema(description = "是否超管（超管拥有全部权限） 0-否 1-是")
    private Byte isAdmin;

    @Schema(description = "状态 0-停用 1-启用")
    private Byte state;

    @Schema(description = "所属系统： MGR-运营平台, MCH-商户中心")
    private String sysType;

    @Schema(description = "所属商户ID / 0(平台)")
    private String belongInfoId;

    @Schema(description = "创建时间")

    private LocalDateTime createdAt;

    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;
}

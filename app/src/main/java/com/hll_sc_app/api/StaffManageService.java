package com.hll_sc_app.api;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.http.HttpFactory;
import com.hll_sc_app.bean.staff.DepartmentListResp;
import com.hll_sc_app.bean.staff.EmployeeBean;
import com.hll_sc_app.bean.staff.RolePermissionResp;
import com.hll_sc_app.bean.staff.RoleResp;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * 员工管理
 *
 * @author zhuyingsong
 * @date 2019-07-25
 */
public interface StaffManageService {
    StaffManageService INSTANCE = HttpFactory.create(StaffManageService.class);

    /**
     * 获取供应商员工列表
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:101025")
    Observable<BaseResp<List<EmployeeBean>>> queryStaffList(@Body BaseMapReq req);

    /**
     * 查询员工数量
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:101103")
    Observable<BaseResp<EmployeeBean>> queryStaffNum(@Body BaseMapReq req);

    /**
     * 删除供应商员工
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:101027")
    Observable<BaseResp<Object>> delStaff(@Body BaseMapReq req);

    /**
     * 修改供应商员工
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:101079")
    Observable<BaseResp<Object>> editStaff(@Body BaseReq<EmployeeBean> req);

    /**
     * 添加供应商员工
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:101026")
    Observable<BaseResp<Object>> addStaff(@Body BaseReq<EmployeeBean> req);

    /**
     * 获取角色权限列表
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:101076")
    Observable<BaseResp<List<RolePermissionResp>>> queryRolesPermission(@Body BaseMapReq req);

    /**
     * 查询员工岗位
     *
     * @param req req
     * @return resp
     */
    @POST(HttpConfig.URL)
    @Headers("pv:104124")
    Observable<BaseResp<RoleResp>> queryRoles(@Body BaseMapReq req);


    /***
     * 查询部门列表
     * @param req
     * @return
     */
    @POST(HttpConfig.URL)
    @Headers("pv:101117")
    Observable<BaseResp<DepartmentListResp>> queryDepartments(@Body BaseMapReq req);

    /***
     * 新增部门列表
     * @param req
     * @return
     */
    @POST(HttpConfig.URL)
    @Headers("pv:101114")
    Observable<BaseResp<Object>> addDepartment(@Body BaseMapReq req);


    /***
     * 删除部门
     * @param req
     * @return
     */
    @POST(HttpConfig.URL)
    @Headers("pv:101116")
    Observable<BaseResp<Object>> delDepartment(@Body BaseMapReq req);

    /***
     * 编辑部门
     * @param req
     * @return
     */
    @POST(HttpConfig.URL)
    @Headers("pv:101115")
    Observable<BaseResp<Object>> mdfDepartment(@Body BaseMapReq req);
}

package com.topshow.service;

import com.topshow.entity.Admin;
import com.topshow.entity.Result;

import java.util.List;

/**
 * 管理员操作接口
 * @author Administrator
 *
 */
public interface AdminService {

    /**
     * 管理员登陆方法
     * @param admin_name
     * @param admin_password
     * @return
     */
    Result login(String admin_name, String admin_password);

    List<Admin> findAllAdmin();

    Result add(Admin admin,String store);

    /**
     * 编辑管理员
     * @param admin
     * @param store
     * @return
     */
    Integer edit(Admin admin, String store);

    Integer del(String admin_id);
}
